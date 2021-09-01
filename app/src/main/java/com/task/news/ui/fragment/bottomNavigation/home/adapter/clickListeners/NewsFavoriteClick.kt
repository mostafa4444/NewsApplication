package com.task.news.ui.fragment.bottomNavigation.home.adapter.clickListeners

import com.task.news.model.response.news.Article

interface NewsFavoriteClick {
    fun newClickFavorite(article: Article , position: Int)
}