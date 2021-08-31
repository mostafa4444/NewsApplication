package com.task.news.model.response.news

data class HeadlineResponse(
        val articles: List<Article>,
        val status: String,
        val totalResults: Int
)