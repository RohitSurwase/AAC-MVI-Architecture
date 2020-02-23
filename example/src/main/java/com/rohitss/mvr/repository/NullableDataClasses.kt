package com.rohitss.mvr.repository

import java.util.*

data class NewsApiResponse(
    val articles: List<NewsItemNullable?>? = null,
    val status : Boolean,
    val totalResults : Long
)

data class NewsItemNullable(
    val urlToImage: String? = null,
    val description: String? = null,
    val title: String? = null,
    val publishedAt : Date
)