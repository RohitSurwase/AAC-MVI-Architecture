package com.rohitss.mvr.api

import com.rohitss.mvr.API_KEY
import com.rohitss.mvr.BASE_URL
import com.rohitss.mvr.repository.NewsApiResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NewsApi {
    @GET("top-headlines?category=technology&pageSize=40&apiKey=$API_KEY")
    suspend fun getLatestNews(): NewsApiResponse

    companion object {
        fun create(): NewsApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsApi::class.java)
        }
    }
}