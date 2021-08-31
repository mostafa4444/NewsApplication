package com.task.news.ui.fragment.bottomNavigation.search

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.news.base.BaseViewModel
import com.task.news.local.LocalRepoImpl
import com.task.news.model.request.HeadlineRequest
import com.task.news.model.response.news.HeadlineResponse
import com.task.news.ui.usecases.HeadlineUseCase
import com.task.news.ui.usecases.SearchUseCase
import com.task.news.utils.LiveDataResource
import com.task.news.utils.constant.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
        private val repository: LocalRepoImpl,
        private val searchUseCase: HeadlineUseCase
) : BaseViewModel() {
    override fun stop() {
    }

    override fun start() {
    }
    fun getMyFilterModel() = repository.fetchFilterModel()


    private val _searchNews: MutableStateFlow<LiveDataResource<HeadlineResponse>> = MutableStateFlow(LiveDataResource.IDLE())
    val searchNews: StateFlow<LiveDataResource<HeadlineResponse>> get() = _searchNews


    fun fetchNews(queryTxt: String = "" , category: String = ""){
        _searchNews.value = LiveDataResource.Loading()
        val requestModel = HeadlineRequest(
                q = queryTxt,
                category = category
        )
        headerParams["X-Api-Key"] = AppConstants.API_KEY
        searchUseCase.execute({
            onComplete {
                _searchNews.value = LiveDataResource.Success(it)
            }
            onError {
                _searchNews.value = LiveDataResource.Error()
            }
            onCancel {
                _searchNews.value = LiveDataResource.Error()

            }
        } , requestModel.serializeToMap().toMutableMap() , headerParams)
    }


    private fun <T> T.serializeToMap(): Map<String, Any> {
        return convert()
    }
    private inline fun <I, reified O> I.convert(): O {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<O>() {}.type)
    }
}