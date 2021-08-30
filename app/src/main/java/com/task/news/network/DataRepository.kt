package com.task.news.network

import javax.inject.Inject

class DataRepository  @Inject constructor(private val apiService: ApiService) : RemoteRepository {
}