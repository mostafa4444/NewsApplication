package com.task.news.utils

sealed class LiveDataResource<T>(
        val data: T? = null,
        val message: String? = null
) {
    class Success<T>(data: T) : LiveDataResource<T>(data)
    class NoData<T>(data: T) : LiveDataResource<T>(data)
    class Loading<T> : LiveDataResource<T>()
    class Error<T> : LiveDataResource<T>()
    class IDLE<T> : LiveDataResource<T>()
    class NoNetwork<T> : LiveDataResource<T>()
}