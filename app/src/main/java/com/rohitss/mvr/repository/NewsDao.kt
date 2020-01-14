package com.rohitss.mvr.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(articlesItem: List<NewsItem>)

    @Query("DELETE FROM news_table")
    fun deleteAllNews()

    @Query("SELECT * from news_table ORDER BY publishedAt")
    fun getAllNewsByDate(): LiveData<List<NewsItem>>
}
