package com.task.news.model.request

data class HeadlineRequest(
        val country: String,
        val pageSize: Int,
        val page: Int,
        val q : String = "",
        val category :String = ""
)
