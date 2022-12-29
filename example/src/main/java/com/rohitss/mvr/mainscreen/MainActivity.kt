package com.rohitss.mvr.mainscreen

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.rohitss.aacmvi.activity.AacMviActivity
import com.rohitss.mvr.databinding.ActivityMainBinding
import com.rohitss.mvr.repository.NewsItem
import com.rohitss.mvr.toast

class MainActivity : AacMviActivity<MainViewState, MainViewEffect, MainViewEvent, MainActVM>() {
    override val viewModel: MainActVM by viewModels()

    private lateinit var binding: ActivityMainBinding

    private val newsRvAdapter by lazy {
        NewsRvAdapter {
            viewModel.process(MainViewEvent.NewsItemClicked(it.tag as NewsItem))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            rvNewsHome.adapter = newsRvAdapter

            srlNewsHome.setOnRefreshListener {
                viewModel.process(MainViewEvent.OnSwipeRefresh)
            }

            fabStar.setOnClickListener {
                viewModel.process(MainViewEvent.FabClicked)
            }
        }
    }

    override fun renderViewState(viewState: MainViewState) {
        when (viewState.fetchStatus) {
            is FetchStatus.Fetched -> {
                binding.srlNewsHome.isRefreshing = false
            }
            is FetchStatus.NotFetched -> {
                viewModel.process(MainViewEvent.FetchNews)
                binding.srlNewsHome.isRefreshing = false
            }
            is FetchStatus.Fetching -> {
                binding.srlNewsHome.isRefreshing = true
            }
        }
        newsRvAdapter.submitList(viewState.newsList)
    }

    override fun renderViewEffect(viewEffect: MainViewEffect) {
        when (viewEffect) {
            is MainViewEffect.ShowSnackbar -> {
                Snackbar.make(
                    binding.coordinatorLayoutRoot,
                    viewEffect.message,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            is MainViewEffect.ShowToast -> {
                toast(message = viewEffect.message)
            }
        }
    }
}

