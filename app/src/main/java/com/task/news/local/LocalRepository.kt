package com.task.news.local


interface LocalRepository {

    fun saveLogin()
    fun returnBoolean(key: String): Boolean
    fun saveBoolean(key: String , success: Boolean)
    fun clearPrefs()
    fun saveInt(key: String , value: Int)
    fun returnInt(key: String):Int
    fun returnLanguage(): String
    fun saveString(key: String , value: String)
    fun returnString(key: String): String
}