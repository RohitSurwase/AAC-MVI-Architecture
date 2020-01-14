package com.rohitss.mvr.repository

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "news_table", indices = [Index("title", unique = true)])
data class NewsItem(
    @PrimaryKey(autoGenerate = true) @NonNull var id: Long,
    var description: String,
    var title: String,
    var imageUrl: String,
    var publishedAt: Long
)
