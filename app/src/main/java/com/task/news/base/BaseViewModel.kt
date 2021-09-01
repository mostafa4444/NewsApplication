package com.task.news.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.news.local.LocalRepoImpl
import com.task.news.model.response.news.Article
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var repository: LocalRepoImpl
    var errorDialog: MutableLiveData<String> = MutableLiveData()
    var successDialog: MutableLiveData<String> = MutableLiveData()
    var queryParams = mutableMapOf<String, Any>()
    var headerParams = mutableMapOf<String , String>()

    abstract fun stop()
    abstract fun start()


    var networkStatus = false

    fun insertArticleToDatabase(article: Article){
        viewModelScope.launch {
            val reponse =  repository.insertArticle(article)
            if (reponse > 0){
                Timber.e("Insertion Success")
            }else{
                Timber.e("Insertion Failed")
            }
        }
    }

}