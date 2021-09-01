package com.task.news.ui.fragment.bottomNavigation.favorite

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.news.R
import com.task.news.base.BaseFragment
import com.task.news.databinding.FavoriteFragmentBinding
import com.task.news.model.response.news.Article
import com.task.news.ui.fragment.bottomNavigation.home.HomeFragmentDirections
import com.task.news.ui.fragment.bottomNavigation.home.adapter.NewsAdapter
import com.task.news.ui.fragment.bottomNavigation.home.adapter.clickListeners.ArticleClickEvent
import com.task.news.ui.fragment.bottomNavigation.home.adapter.clickListeners.NewsFavoriteClick
import com.task.news.utils.WidgetUtils.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FavoriteViewModel, FavoriteFragmentBinding>() {

    var adapter : NewsAdapter ?= null
    var items: MutableList<Article> ?= null
    override fun initView() {
        adapter = NewsAdapter()
    }
    override fun getContentView(): Int = R.layout.favorite_fragment

    override fun initializeViewModel() {
        val viewModel: FavoriteViewModel by viewModels()
        baseViewModel = viewModel
    }

    override fun onClick(v: View?) {
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        bindDataFromRoom()
    }

    private fun bindDataFromRoom(){
        lifecycleScope.launchWhenStarted {
            baseViewModel?.fetchArticlesFromRoom()?.collect {
                items = it.toMutableList()
                initCategoryRecycler(baseViewBinding.newsRecycler , items!!)
            }
        }
    }

    private val articleClickCallback = object : ArticleClickEvent {
        override fun articleClickEvent(article: Article) {
            val direction = FavoriteFragmentDirections.actionFavoriteFragmentToWebFragment(article.url.toString())
            findNavController().navigate(direction)
        }
    }

    private fun initCategoryRecycler(recyclerView: RecyclerView, itemList: MutableList<Article>){
        recyclerView.layoutManager = LinearLayoutManager(context ,  LinearLayoutManager.VERTICAL , false)
        adapter = NewsAdapter().apply {
            submitMyList(itemList , favoriteClickCallback , articleClickCallback,true)
        }
        recyclerView.setVisible()
        recyclerView.adapter = adapter
        recyclerView.startLayoutAnimation()
    }
    private val favoriteClickCallback = object : NewsFavoriteClick {
        override fun newClickFavorite(article: Article, position: Int) {
            baseViewModel?.deleteArticle(article)
            items?.remove(article)
            adapter?.notifyItemRemoved(position)

        }

    }

}