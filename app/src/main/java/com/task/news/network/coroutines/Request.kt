package com.task.news.network.coroutines

import kotlinx.coroutines.CancellationException

class Request<T> {
    private var onComplete: ((T) -> Unit)? = null
    private var onError: ((Throwable) -> Unit)? = null
    private var onCancel: ((CancellationException) -> Unit)? = null


    fun onComplete(block: (T) -> Unit) {
        onComplete = block
    }

    fun onError(block: (Throwable) -> Unit) {
        onError = block
    }

    fun onCancel(block: (CancellationException) -> Unit) {
        onCancel = block
    }

    operator fun invoke(result: T) {
        onComplete?.invoke(result)
    }

    operator fun invoke(error: Throwable) {
        onError?.invoke(error)
    }

    operator fun invoke(error: CancellationException) {
        onCancel?.invoke(error)
    }

}