package com.task.news.utils.constant

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ClassConversion {
    fun <T> T.serializeToMap(): Map<String, Any> {
        return convert()
    }

    inline fun <I, reified O> I.convert(): O {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }
}