package com.task.news.ui.fragment.bottomNavigation.search

import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.news.base.BaseViewModel
import com.task.news.local.LocalRepoImpl
import com.task.news.model.request.HeadlineRequest
import com.task.news.model.response.news.Article
import com.task.news.model.response.news.HeadlineResponse
import com.task.news.ui.usecases.HeadlineUseCase
import com.task.news.utils.LiveDataResource
import com.task.news.utils.constant.AppConstants
import com.task.news.utils.constant.ClassConversion.serializeToMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel  @Inject constructor(
        private val headlineUseCase: HeadlineUseCase
) : BaseViewModel() {
    override fun stop() {
    }

    override fun start() {
    }

    fun getMyFilterModel() = repository.fetchFilterModel()

    private val _searchNews: MutableStateFlow<LiveDataResource<HeadlineResponse>> = MutableStateFlow(LiveDataResource.IDLE())
    val searchNews: StateFlow<LiveDataResource<HeadlineResponse>> get() = _searchNews


    fun searchNews(queryTxt: String = "", category: String = "") {
        _searchNews.value = LiveDataResource.Loading()
        val requestModel = HeadlineRequest(
                q = queryTxt,
                category = category,
                country = getMyFilterModel().country
        )
        headerParams["X-Api-Key"] = AppConstants.API_KEY
        headlineUseCase.execute({
            onComplete {
                if (it.articles.isNotEmpty()){
                    _searchNews.value = LiveDataResource.Success(it)
                }else{
                    _searchNews.value = LiveDataResource.NoData(it)
                }
            }
            onError {
                if (networkStatus){
                    _searchNews.value = LiveDataResource.Error()
                }else{
                    _searchNews.value = LiveDataResource.NoNetwork()
                }
            }
            onCancel {
                _searchNews.value = LiveDataResource.Error()

            }
        }, requestModel.serializeToMap().toMutableMap(), headerParams)
    }


}