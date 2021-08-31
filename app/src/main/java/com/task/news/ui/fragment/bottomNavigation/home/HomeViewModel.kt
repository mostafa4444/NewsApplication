package com.task.news.ui.fragment.bottomNavigation.home

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.news.base.BaseViewModel
import com.task.news.local.LocalRepoImpl
import com.task.news.model.request.HeadlineRequest
import com.task.news.model.response.news.HeadlineResponse
import com.task.news.ui.usecases.HeadlineUseCase
import com.task.news.utils.LiveDataResource
import com.task.news.utils.constant.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
        private val repository: LocalRepoImpl,
        private val headlineUseCase: HeadlineUseCase
) : BaseViewModel() {
    override fun stop() {
    }

    override fun start() {
        fetchNews()
    }

    fun getMyFilterModel() = repository.fetchFilterModel()


    private val _headlineNews: MutableStateFlow<LiveDataResource<HeadlineResponse>> = MutableStateFlow(LiveDataResource.IDLE())
    val headlineNews: StateFlow<LiveDataResource<HeadlineResponse>> get() = _headlineNews


    fun fetchNews(category: String = ""){
        val data = repository.fetchFilterModel()
        val requestCategory = if (category.isNullOrEmpty()){
            data.categories[0].name
        }else{
            category
        }
        _headlineNews.value = LiveDataResource.Loading()
        val requestModel = HeadlineRequest(
                country = data.country,
                pageSize = 100,
                page = 1,
                category = requestCategory
        )
        headerParams["X-Api-Key"] = AppConstants.API_KEY
        headlineUseCase.execute({
            onComplete {
                _headlineNews.value = LiveDataResource.Success(it)
            }
            onError {
                _headlineNews.value = LiveDataResource.Error()
            }
            onCancel {
                _headlineNews.value = LiveDataResource.Error()

            }
        } , requestModel.serializeToMap().toMutableMap() , headerParams)
    }

    fun cancelAndStartNewCall(category:String){
        headlineUseCase.unsubscribe()
        fetchNews(category)
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