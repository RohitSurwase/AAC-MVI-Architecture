package com.rohitss.mvr.view

import android.os.Bundle
import androidx.activity.viewModels
import com.rohitss.aacmvi.AacMviActivity
import com.rohitss.mvr.R
import com.rohitss.mvr.repository.NewsItem
import com.rohitss.mvr.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AacMviActivity<MainViewState, MainViewEffect, MainViewEvent, MainViewActVM>() {
    override val viewModel: MainViewActVM by viewModels()

    private val newsRvAdapter by lazy {
        NewsRvAdapter {
            toast("Clicked ${(it.tag as NewsItem).title}")
        }.apply { setHasStableIds(true) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvNewsHome.adapter = newsRvAdapter

        srlNewsHome.setOnRefreshListener {
            if (srlNewsHome.isRefreshing) srlNewsHome.isRefreshing = false
        }

        fabStar.setOnClickListener {

        }
    }

    override fun renderViewState(viewState: MainViewState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun renderViewEffect(viewEffect: MainViewEffect?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

