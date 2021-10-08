package com.rohitss.mvr.mockapi

import com.rohitss.mvr.repository.NewsItem

data class MockApiResponse(
    val articles: List<NewsItem>? = null
)