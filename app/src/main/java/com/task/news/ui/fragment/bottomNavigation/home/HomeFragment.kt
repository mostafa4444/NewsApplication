package com.task.news.ui.fragment.bottomNavigation.home

import android.content.res.ColorStateList
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.news.R
import com.task.news.base.BaseFragment
import com.task.news.databinding.HomeFragmentBinding
import com.task.news.model.prefsModel.CategoryModel
import com.task.news.model.response.news.Article
import com.task.news.ui.fragment.bottomNavigation.home.adapter.clickListeners.CategoryClickEvent
import com.task.news.ui.fragment.bottomNavigation.home.adapter.NewsAdapter
import com.task.news.ui.fragment.bottomNavigation.home.adapter.clickListeners.NewsFavoriteClick
import com.task.news.ui.fragment.bottomNavigation.home.adapter.SelectedCategoryRecycler
import com.task.news.ui.fragment.bottomNavigation.home.adapter.clickListeners.ArticleClickEvent
import com.task.news.utils.LiveDataResource
import com.task.news.utils.WidgetUtils.setGone
import com.task.news.utils.WidgetUtils.setVisible

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {
    var def : ColorStateList?= null
    var newsArticle: MutableList<Article> ?= null
    override fun initView() {
        bindCategories()
    }

    override fun getContentView(): Int = R.layout.home_fragment

    override fun initializeViewModel() {
        val viewModel: HomeViewModel by viewModels()
        baseViewModel = viewModel
    }

    private fun bindCategories(){
        baseViewModel?.getMyFilterModel()?.categories?.let {
            initCategoryRecycler(baseViewBinding.categoryRecycler , it.toMutableList())
        }

    }


    override fun onClick(v: View?) {
        when(v){
        }
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        bindHeadlineNewsData()
    }

    private fun initNewsAdapter(recyclerView: RecyclerView, itemList: MutableList<Article>){
        recyclerView.layoutManager = LinearLayoutManager(context ,  LinearLayoutManager.VERTICAL , false)
        val adapter = NewsAdapter().apply {
            submitMyList(itemList , favoriteClickCallback , articleClickCallback)
        }
        recyclerView.setVisible()
        recyclerView.adapter = adapter
        recyclerView.startLayoutAnimation()
    }

    private val favoriteClickCallback = object : NewsFavoriteClick {
        override fun newClickFavorite(article: Article , position: Int) {
           baseViewModel?.insertArticleToDatabase(article)
        }
    }

    private val articleClickCallback = object : ArticleClickEvent {
        override fun articleClickEvent(article: Article) {
            val direction = HomeFragmentDirections.actionHomeFragmentToWebFragment(article.url.toString())
            findNavController().navigate(direction)
        }
    }

    private val categoryClickEventImpl = object : CategoryClickEvent {
        override fun categoryClicked(categoryModel: CategoryModel) {
            baseViewModel?.cancelAndStartNewCall(categoryModel.name)
        }

    }



    private fun initCategoryRecycler(recyclerView: RecyclerView, itemList: MutableList<CategoryModel>){
        recyclerView.layoutManager = LinearLayoutManager(context ,  LinearLayoutManager.HORIZONTAL , false)
        val adapter = SelectedCategoryRecycler().apply {
            submitMyList(itemList , categoryClickEventImpl)
        }
        recyclerView.setVisible()
        recyclerView.adapter = adapter
        recyclerView.startLayoutAnimation()
    }
    private fun bindHeadlineNewsData(){
        lifecycleScope.launchWhenStarted {
            baseViewModel?.headlineNews?.collect{
                when(it){
                    is LiveDataResource.Success -> {
                        Timber.e("Size is ${it.data?.articles?.size}")
                        it.data?.articles?.let { data->
                            newsArticle = data.toMutableList()
                            baseViewBinding.newsShimmer.setGone()
                            baseViewBinding.noNetworkLayout.root.setGone()
                            baseViewBinding.noDataLayout.root.setGone()
                            baseViewBinding.serverErrorLayout.root.setGone()
                            initNewsAdapter(baseViewBinding.newsRecycler , data.toMutableList())
                        }
                    }
                    is LiveDataResource.NoData -> {
                        baseViewBinding.newsShimmer.setGone()
                        baseViewBinding.newsRecycler.setGone()
                        baseViewBinding.noNetworkLayout.root.setGone()
                        baseViewBinding.noDataLayout.root.setVisible()
                        baseViewBinding.serverErrorLayout.root.setGone()
                    }
                    is LiveDataResource.Loading -> {
                        baseViewBinding.newsShimmer.setVisible()
                        baseViewBinding.newsRecycler.setGone()
                        baseViewBinding.noNetworkLayout.root.setGone()
                        baseViewBinding.noDataLayout.root.setGone()
                        baseViewBinding.serverErrorLayout.root.setGone()
                    }
                    is LiveDataResource.NoNetwork -> {
                        baseViewBinding.newsShimmer.setGone()
                        baseViewBinding.newsRecycler.setGone()
                        baseViewBinding.noNetworkLayout.root.setVisible()
                        baseViewBinding.noDataLayout.root.setGone()
                        baseViewBinding.serverErrorLayout.root.setGone()
                    }
                    is LiveDataResource.Error -> {
                        baseViewBinding.newsShimmer.setGone()
                        baseViewBinding.newsRecycler.setGone()
                        baseViewBinding.noNetworkLayout.root.setGone()
                        baseViewBinding.noDataLayout.root.setGone()
                        baseViewBinding.serverErrorLayout.root.setVisible()
                    }

                    else->{}
                }
            }
        }
    }


}