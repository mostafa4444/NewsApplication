package com.task.news.di

import android.content.Context
import androidx.room.Room
import com.task.news.network.ApiService
import com.task.news.network.DataRepository
import com.task.news.local.persistance.AppDatabase
import com.task.news.utils.constant.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {

    /*
    Module which provides my DataRepository and DAO interface which contains room database queries
 */
    @ViewModelScoped
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context ,
        AppDatabase::class.java,
        AppConstants.ROOM_DATABASE_NAME
    ).build()


    @Provides
    @ViewModelScoped
    fun providesDataRepository(apiService: ApiService): DataRepository {
        return DataRepository(apiService)
    }

    @Provides
    @ViewModelScoped
    fun provideRunningDao(db: AppDatabase) = db.userDao()

}
