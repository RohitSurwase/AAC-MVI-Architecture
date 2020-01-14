package com.rohitss.mvr.repository

import android.util.Log
import com.rohitss.mvr.API_KEY
import com.rohitss.mvr.BASE_URL
import com.rohitss.mvr.TAG
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines?category=technology&pageSize=20&apiKey=$API_KEY")
    suspend fun getLatestNews(@Query(value = "page") page: Int): NewsApiResponse

    companion object {
        fun create(): NewsApi {
            val logger = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d(TAG, message)
                }
            })

            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsApi::class.java)
        }
    }
}