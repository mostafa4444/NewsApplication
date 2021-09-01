package com.task.news.ui.fragment.bottomNavigation.home

import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
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
                if (it.articles.isNotEmpty()){
                    _headlineNews.value = LiveDataResource.Success(it)
                }else{
                    _headlineNews.value = LiveDataResource.NoData(it)
                }
            }
            onError {
                if (networkStatus){
                    _headlineNews.value = LiveDataResource.Error()
                }else{
                    _headlineNews.value = LiveDataResource.NoNetwork()
                }
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

}