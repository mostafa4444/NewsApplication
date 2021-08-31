package com.task.news.model.request

data class HeadlineRequest(
        val country: String ?= null,
        val pageSize: Int ?= null,
        val page: Int ?= null,
        val q : String = "",
        val category :String = ""
)
