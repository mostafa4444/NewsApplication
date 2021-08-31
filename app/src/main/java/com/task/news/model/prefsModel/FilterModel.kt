package com.task.news.model.prefsModel

import com.task.news.model.CategoryModel

data class FilterModel(
        val categories: List<CategoryModel> ,
        val country: String
)
