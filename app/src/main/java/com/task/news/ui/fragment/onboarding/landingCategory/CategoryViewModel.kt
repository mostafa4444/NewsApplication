package com.task.news.ui.fragment.onboarding.landingCategory

import androidx.lifecycle.ViewModel
import com.task.news.base.BaseViewModel
import com.task.news.local.LocalRepoImpl
import com.task.news.model.prefsModel.FilterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
) : BaseViewModel() {
    override fun stop() {
    }

    override fun start() {
    }

    fun saveFilterModel_saveSelectionState(filterModel: FilterModel){
        repository.submitSelectionProcess(filterModel)
    }
}