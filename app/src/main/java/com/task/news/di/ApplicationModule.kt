package com.task.news.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.task.news.local.preference.PreferencesManager
import com.task.news.utils.NetworkConnection
import com.task.news.utils.constant.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context) =
        NetworkConnection(context as Application)


    @Singleton
    @Provides
    fun getPreferencesManager(sharedPreferences: SharedPreferences): PreferencesManager {
        return PreferencesManager(sharedPreferences)
    }

    @Singleton
    @Provides
    fun getSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(AppConstants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

}