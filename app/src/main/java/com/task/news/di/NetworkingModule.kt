package com.task.news.di

import com.task.news.network.ApiService
import com.task.news.utils.constant.AppConstants.BASE_URL
import com.task.news.utils.constant.AppConstants.HTTP_TIMEOUT
import com.task.news.utils.constant.AppConstants.TIME_UNIT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {
    /*
        Module which provides my connection status { if the device connected to network or not } &
        Module which provides my Retrofit and API services
     */
    @Singleton
    @Provides
    fun providesBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesHttpClient(logger: HttpLoggingInterceptor) : OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(logger)
        okHttpClient.callTimeout(HTTP_TIMEOUT , TIME_UNIT)
        okHttpClient.connectTimeout(HTTP_TIMEOUT , TIME_UNIT)
        okHttpClient.writeTimeout(HTTP_TIMEOUT , TIME_UNIT)
        okHttpClient.readTimeout(HTTP_TIMEOUT , TIME_UNIT)
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun providesConverterFactory(): Converter.Factory{
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesRetrofit(base_url: String, colverFactory: Converter.Factory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(colverFactory)
                .client(okHttpClient)
                .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


}
