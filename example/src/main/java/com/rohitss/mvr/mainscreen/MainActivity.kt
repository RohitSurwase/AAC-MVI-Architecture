package com.rohitss.mvr.mainscreen

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.rohitss.aacmvi.AacMviActivity
import com.rohitss.mvr.R
import com.rohitss.mvr.repository.NewsItem
import com.rohitss.mvr.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AacMviActivity<MainViewState, MainViewEffect, MainViewEvent, MainActVM>() {
    override val viewModel: MainActVM by viewModels()

    private val newsRvAdapter by lazy {
        NewsRvAdapter {
            viewModel.process(MainViewEvent.NewsItemClicked(it.tag as NewsItem))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvNewsHome.adapter = newsRvAdapter

        srlNewsHome.setOnRefreshListener {
            if (srlNewsHome.isRefreshing) srlNewsHome.isRefreshing = false
            viewModel.process(MainViewEvent.OnSwipeRefresh)
        }

        fabStar.setOnClickListener {
            viewModel.process(MainViewEvent.FabClicked)
        }
    }

    override fun renderViewState(viewState: MainViewState) {
        when (viewState.fetchStatus) {
            is FetchStatus.Fetched -> {
                srlNewsHome.isRefreshing = false
            }
            is FetchStatus.NotFetched -> {
                viewModel.process(MainViewEvent.FetchNews)
                srlNewsHome.isRefreshing = false
            }
            is FetchStatus.Fetching -> {
                srlNewsHome.isRefreshing = true
            }
        }
        newsRvAdapter.submitList(viewState.newsList)
    }

    override fun renderViewEffect(viewEffect: MainViewEffect) {
        when (viewEffect) {
            is MainViewEffect.ShowSnackbar -> {
                Snackbar.make(coordinatorLayoutRoot, viewEffect.message, Snackbar.LENGTH_SHORT).show()
            }
            is MainViewEffect.ShowToast -> {
                toast(message = viewEffect.message)
            }
        }
    }
}

