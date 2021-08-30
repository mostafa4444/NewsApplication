package com.task.news.network.coroutines

import com.task.news.network.DataRepository
import kotlinx.coroutines.*
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext



typealias CompletionBlock<T> = Request<T>.() -> Unit

abstract class BaseUseCase<T>(protected var dataRepository: DataRepository) {

    private var parentJob: Job = Job()
    var backgroundContext: CoroutineContext = Dispatchers.IO
    var foregroundContext: CoroutineContext = Dispatchers.Main

    protected abstract suspend fun executeOnBackground(map: MutableMap<String, Any> , headerMap: Map<String , Any> ?= null): T

    fun execute(block: CompletionBlock<T>, map: MutableMap<String, Any> , headerMap: Map<String, Any>?= null) {
        val response = Request<T>().apply { block() }
        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch {
            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground(map , headerMap)
                }
                response(result)

            } catch (unknownException: UnknownHostException) {
                response(unknownException)
            } catch (cancellationException: CancellationException) {
                response(cancellationException)
            } catch (e: Exception) {
                response(e)
            }
        }
    }

    protected suspend fun <X> runAsync(
        context: CoroutineContext = backgroundContext,
        block: suspend () -> X
    ): Deferred<X> {
        return CoroutineScope(context + parentJob).async {
            block.invoke()
        }
    }

    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

}