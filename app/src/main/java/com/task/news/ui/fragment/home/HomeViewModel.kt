package com.task.news.ui.fragment.home

import com.task.news.base.BaseViewModel
import com.task.news.local.LocalRepoImpl
import com.task.news.utils.constant.PrefsKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
        private val repository: LocalRepoImpl
) : BaseViewModel() {
    override fun stop() {
    }

    override fun start() {
    }

    fun checkOnBoardingStatus(): Boolean{
        return repository.returnBoolean(PrefsKeys.ON_BOARDING_SELECTION)
    }

    fun getMyFilterModel() = repository.fetchFilterModel()

}