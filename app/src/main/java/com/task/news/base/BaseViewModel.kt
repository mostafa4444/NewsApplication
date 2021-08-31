package com.task.news.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel() : ViewModel() {



    var errorDialog: MutableLiveData<String> = MutableLiveData()
    var successDialog: MutableLiveData<String> = MutableLiveData()
    var queryParams = mutableMapOf<String, Any>()
    var headerParams = mutableMapOf<String , String>()

    abstract fun stop()
    abstract fun start()


    var networkStatus = false



}