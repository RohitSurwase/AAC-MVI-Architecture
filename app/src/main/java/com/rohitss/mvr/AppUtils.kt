package com.rohitss.mvr

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.rohitss.mvr.repository.AppDatabase
import com.rohitss.mvr.repository.NewsRepository

const val DATABASE_NAME = "my_database.db"
const val DATABASE_VERSION = 1
const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = BuildConfig.NewsApiKey

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

val View.setVisible: Unit
    get() {
        visibility = View.VISIBLE
    }

val View.setGone: Unit
    get() {
        visibility = View.GONE
    }

val View.setInvisible: Unit
    get() {
        visibility = View.INVISIBLE
    }

fun inflate(context: Context, viewId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(viewId, parent, attachToRoot)
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun getNewsRepository(context: Context): NewsRepository {
    return NewsRepository.getInstance(AppDatabase.getInstance(context).getNewsDAO())
}