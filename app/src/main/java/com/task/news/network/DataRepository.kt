package com.task.news.network

import com.task.news.model.response.news.HeadlineResponse
import com.task.news.network.MyRequestMap.HEADLINE_MAP
import javax.inject.Inject

class DataRepository  @Inject constructor(private val apiService: ApiService) : RemoteRepository {

    override suspend fun fetchHeadlineNews(headerMap: Map<String, String>, map: MutableMap<String, Any>): HeadlineResponse {
        return apiService.fetchHeadLineNews(headerMap , map)
    }

    override suspend fun searchNews(headerMap: Map<String, String>, map: MutableMap<String, Any>): HeadlineResponse {
        return apiService.fetchAllNewsToSearch(headerMap, map)
    }

}