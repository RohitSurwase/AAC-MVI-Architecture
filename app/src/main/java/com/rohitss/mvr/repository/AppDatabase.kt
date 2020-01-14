package com.rohitss.mvr.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rohitss.mvr.DATABASE_NAME
import com.rohitss.mvr.DATABASE_VERSION

@Database(entities = [NewsItem::class], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNewsDAO(): NewsDAO

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance
                ?: synchronized(this) {
                instance
                    ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                        DATABASE_NAME
                ).build().also { instance = it }
            }
        }
    }
}