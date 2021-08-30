package com.task.news.local.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.news.model.User

/*
    Room Database class which responsible for add new entities
 */

@Database(entities = [User::class] ,  version = 1 , exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): DAO

}