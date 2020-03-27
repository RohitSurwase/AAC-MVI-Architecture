package com.rohitss.mvr.mainscreen

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.rohitss.aacmvi.AacMviViewModel
import com.rohitss.mvr.LCE
import com.rohitss.mvr.repository.NewsItem
import com.rohitss.mvr.repository.NewsRepository
import kotlinx.coroutines.launch

class MainActVM(application: Application) :
    AacMviViewModel<MainViewState, MainViewEffect, MainViewEvent>(application) {
    private var count: Int = 0
    private val repository: NewsRepository = NewsRepository.getInstance()

    init {
        viewState = MainViewState(fetchStatus = FetchStatus.NotFetched, newsList = emptyList())
    }

    override fun process(viewEvent: MainViewEvent) {
        super.process(viewEvent)
        when (viewEvent) {
            is MainViewEvent.NewsItemClicked -> newsItemClicked(viewEvent.newsItem)
            MainViewEvent.FabClicked -> fabClicked()
            MainViewEvent.OnSwipeRefresh -> fetchNews()
            MainViewEvent.FetchNews -> fetchNews()
        }
    }

    private fun newsItemClicked(newsItem: NewsItem) {
        viewEffect = MainViewEffect.ShowSnackbar(newsItem.title)
    }

    private fun fabClicked() {
        count++
        viewEffect = MainViewEffect.ShowToast(message = "Fab clicked count $count")
    }

    private fun fetchNews() {
        viewState = viewState.copy(fetchStatus = FetchStatus.Fetching)
        viewModelScope.launch {
            when (val result = repository.getSetLatestNewsFromServer()) {
                is LCE.Error -> {
                    viewState = viewState.copy(fetchStatus = FetchStatus.Fetched)
                    viewEffect = MainViewEffect.ShowToast(message = result.message)
                }
                is LCE.Success -> {
                    viewState =
                        viewState.copy(fetchStatus = FetchStatus.Fetched, newsList = result.data)
                }
            }
        }
    }
}