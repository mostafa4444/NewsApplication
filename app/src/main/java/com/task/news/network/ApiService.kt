package com.task.news.network

import com.google.firebase.database.collection.RBTreeSortedMap
import com.task.news.model.response.news.HeadlineResponse
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap


/*
    Include all API Services
 */

interface ApiService {

    @JvmSuppressWildcards
    @GET("top-headlines")
    suspend fun fetchHeadLineNews(@HeaderMap headers: Map<String, String>, @QueryMap queryMap: Map<String , Any>) : HeadlineResponse


    @JvmSuppressWildcards
    @GET("everything")
    suspend fun fetchAllNewsToSearch(@HeaderMap headers: Map<String, String>, @QueryMap queryMap: Map<String , Any>) : HeadlineResponse

}