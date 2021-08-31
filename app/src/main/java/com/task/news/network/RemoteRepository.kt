package com.task.news.network

import com.task.news.model.response.news.HeadlineResponse

interface RemoteRepository {

    suspend fun fetchHeadlineNews(headerMap: Map<String,String>, map: MutableMap<String, Any>): HeadlineResponse
    suspend fun searchNews(headerMap: Map<String,String>, map: MutableMap<String, Any>): HeadlineResponse

}