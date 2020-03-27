package com.rohitss.mvr.mainscreen

import com.rohitss.mvr.repository.NewsItem


data class MainViewState(val fetchStatus: FetchStatus, val newsList: List<NewsItem>)

sealed class MainViewEffect {
    data class ShowNewsTitleSnackbar(val title: String) : MainViewEffect()
    data class ShowToast(val message: String) : MainViewEffect()
}

sealed class MainViewEvent {
    data class NewsItemClicked(val newsItem: NewsItem) : MainViewEvent()
    object FabClicked : MainViewEvent()
    object OnSwipeRefresh : MainViewEvent()
    object FetchNews : MainViewEvent()
}

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
}