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
import okhttp3.internal.checkOffsetAndCount
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
        private val headlineUseCase: HeadlineUseCase
) : BaseViewModel() {
    override fun stop() {
    }

    override fun start() {
        fetchAllCategories()
    }


    var _myDateLis: MutableList<Article> = mutableListOf()

    private fun fetchAllCategories(){
        getMyFilterModel().categories.let {
            it.forEach {model->
                fetchNews(model.name)
            }
        }
    }

    private fun getMyFilterModel() = repository.fetchFilterModel()

    private val _headlineNews: MutableStateFlow<LiveDataResource<HeadlineResponse>> = MutableStateFlow(LiveDataResource.IDLE())
    val headlineNews: StateFlow<LiveDataResource<HeadlineResponse>> get() = _headlineNews

    private var _allArticle: MutableStateFlow<LiveDataResource<MutableList<Article>>> = MutableStateFlow(LiveDataResource.IDLE())
    val allArticle: StateFlow<LiveDataResource<MutableList<Article>>> get() = _allArticle



    private fun fetchNews(category: String = ""){
        val data = repository.fetchFilterModel()
        _headlineNews.value = LiveDataResource.Loading()
        val requestModel = HeadlineRequest(
                country = data.country,
                pageSize = 20,
                page = 1,
                category = category
        )
        headerParams["X-Api-Key"] = AppConstants.API_KEY
        headlineUseCase.execute({
            onComplete {
                if (it.articles.isNotEmpty()){
                    _headlineNews.value = LiveDataResource.Success(it)
                    _myDateLis.addAll(it.articles)
                    _allArticle.value = LiveDataResource.Success(_myDateLis)
                }else{
                    _headlineNews.value = LiveDataResource.NoData(it)
                }
            }
            onError {
                Timber.e("Error ${it.printStackTrace()}")
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