package com.task.news.local

import com.task.news.model.prefsModel.FilterModel
import com.task.news.model.response.news.Article
import kotlinx.coroutines.flow.Flow


interface LocalRepository {

    fun returnBoolean(key: String): Boolean
    fun saveBoolean(key: String , success: Boolean)
    fun clearPrefs()
    fun saveInt(key: String , value: Int)
    fun returnInt(key: String):Int
    fun saveString(key: String , value: String)
    fun returnString(key: String): String
    fun submitSelectionProcess(filterModel: FilterModel)
    fun fetchFilterModel(): FilterModel
    suspend fun insertArticle(article: Article): Long
    suspend fun fetchNewsFromRoom(): Flow<List<Article>>
    suspend fun deleteArticle(article: Article)
}