package com.task.news.ui.fragment.bottomNavigation.home.adapter.clickListeners

import com.task.news.model.prefsModel.CategoryModel

interface CategoryClickEvent {
    fun categoryClicked(categoryModel: CategoryModel)
}