package com.rohitss.mvr.repository

import com.rohitss.mvr.LCE
import com.rohitss.mvr.api.NewsApi

class NewsRepository {

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: NewsRepository().also { instance = it }
            }
    }

    suspend fun getSetLatestNewsFromServer(): LCE<List<NewsItem>> {
        val articlesApiResult = try {
            NewsApi.create().getLatestNews()
        } catch (e: Exception) {
            return LCE.Error(e)
        }

        articlesApiResult.articles?.let { list ->
            val articlesList: MutableList<NewsItem> = mutableListOf()
            // Server data is nullable so we are filtering items which has null/empty title.
            list.asSequence()
                .filterNotNull()
                .filterNot { it.title.isNullOrEmpty() }
                .map {
                    NewsItem(
                        description = if (it.description.isNullOrEmpty()) "" else it.description,
                        title = it.title!!,
                        imageUrl = if (it.urlToImage.isNullOrEmpty()) "" else it.urlToImage,
                        publishedAt = it.publishedAt.time
                    )
                }
                .toList()
                .forEach { articlesList.add(it) }

            return if (articlesList.isNotEmpty()) {
                LCE.Success(data = articlesList)
            } else {
                LCE.Error("No result found")
            }
        } ?: run {
            return LCE.Error("Failed to get News")
        }
    }
}