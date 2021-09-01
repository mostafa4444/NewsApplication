package com.task.news.local.persistance

import androidx.room.TypeConverter
import com.task.news.model.response.news.Source

class DatabaseConverter {
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name.toString()
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}