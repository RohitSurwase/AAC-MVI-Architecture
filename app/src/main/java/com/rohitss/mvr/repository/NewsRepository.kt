package com.rohitss.mvr.repository

import android.util.Log
import com.rohitss.mvr.TAG

class NewsRepository private constructor(private val newsDAO: NewsDAO) {

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(databaseDAO: NewsDAO) =
            instance ?: synchronized(this) {
                instance
                    ?: NewsRepository(
                        databaseDAO
                    )
                    .also { instance = it }
            }
    }

    fun getAllArticlesByDate() = newsDAO.getAllNewsByDate()

    suspend fun getSetLatestNewsFromServer(page: Int = 1) {
        val articlesApiResult = try {
            NewsApi.create().getLatestNews(page)
        } catch (e: Exception) {
            Log.e(TAG, "getSetLatestNewsFromServer error: ${e.message}", e)
            null
        }

        articlesApiResult?.let { apiResult ->
            apiResult.articles?.let { list ->
                val articlesList = mutableListOf<NewsItem>()
                // Server data is nullable so we are filtering items which has null/empty title.
                list.asSequence()
                    .filterNotNull()
                    .filterNot { it.title.isNullOrEmpty() }
                    .map {
                        NewsItem(
                            id = 0,
                            description = if (it.description.isNullOrEmpty()) "" else it.description,
                            title = it.title!!,
                            imageUrl = if (it.urlToImage.isNullOrEmpty()) "" else it.urlToImage,
                            publishedAt = it.publishedAt.time
                        )
                    }
                    .toList()
                    .forEach { articlesList.add(it) }

                if (articlesList.isNotEmpty()) {
                    newsDAO.insertAll(articlesList)
                } else {
                    Log.e(TAG, "No result found")
                }
            }
        }
    }
}