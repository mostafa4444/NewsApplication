package com.task.news.ui.fragment.bottomNavigation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.news.base.BaseViewModel
import com.task.news.local.LocalRepoImpl
import com.task.news.model.response.news.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
) : BaseViewModel() {
    override fun stop() {
    }

    override fun start() {
    }

    suspend fun fetchArticlesFromRoom() : Flow<List<Article>> = repository.fetchNewsFromRoom()

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }
}