package com.task.news.model.response.news

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Article")
data class Article(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        val author: String?,
        val content: String?,
        val description: String?,
        val publishedAt: String?,
        val source: Source,
        val title: String?,
        val url: String?,
        val urlToImage: String?,
        val isFavorite: Boolean = false
)