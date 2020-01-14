package com.rohitss.mvr.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rohitss.mvr.R
import com.rohitss.mvr.repository.NewsItem
import com.rohitss.mvr.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
}
