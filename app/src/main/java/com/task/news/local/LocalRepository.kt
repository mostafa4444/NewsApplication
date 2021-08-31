package com.task.news.local

import com.task.news.model.prefsModel.FilterModel


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
}