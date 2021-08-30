package com.task.news.local.preference

import android.content.SharedPreferences
import com.google.gson.Gson

class PreferencesManager(private val sharedPreferences: SharedPreferences) {
    private val editor: SharedPreferences.Editor = this.sharedPreferences.edit()

    fun saveString(key: String , value: String){
        editor.putString(key , value).apply()
    }
//
//    fun returnLanguage(): String? {
//        return sharedPreferences.getString(AppConstants.APP_LANGUAGE , "en")
//    }

    fun saveInt(key: String , value: Int){
        editor.putInt(key , value).apply()
    }

    fun saveBoolean(key: String , value: Boolean){
        editor.putBoolean(key , value).apply()
    }

    fun saveFloat(key: String , value: Float){
        editor.putFloat(key , value).apply()
    }

    fun returnString(key: String): String{
        return sharedPreferences.getString(key , "").toString()
    }

    fun returnInt(key: String): Int{
        return sharedPreferences.getInt(key , 0)
    }

    fun returnBoolean(key: String): Boolean{
        return sharedPreferences.getBoolean(key , false)
    }

    fun returnFloat(key: String): Float{
        return sharedPreferences.getFloat(key , 0f)
    }


    fun <Model> getObject(key: String, modelClass: Class<Model>): Model  {
        val json = this.sharedPreferences.getString(key, null)
        val gson = Gson()
        return gson.fromJson(json, modelClass)
    }

    fun saveObject(key: String, model: Any) {
        editor.putString(key, Gson().toJson(model))
        editor.commit()
    }

    fun clearSharedPreferences() {
        this.sharedPreferences.edit().clear().apply()
    }


}

