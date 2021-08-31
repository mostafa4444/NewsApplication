package com.task.news.local.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.news.model.User
import com.task.news.model.response.news.Article

/*
    Room Database class which responsible for add new entities
 */

@Database(entities = [Article::class] ,  version = 1 , exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): DAO

}