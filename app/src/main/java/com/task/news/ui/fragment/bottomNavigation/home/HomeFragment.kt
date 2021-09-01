package com.task.news.ui.fragment.bottomNavigation.home

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
import com.task.news.ui.fragment.bottomNavigation.home.adapter.NewsAdapter
import com.task.news.ui.fragment.bottomNavigation.home.adapter.clickListeners.NewsFavoriteClick
import com.task.news.ui.fragment.bottomNavigation.home.adapter.clickListeners.ArticleClickEvent
import com.task.news.utils.LiveDataResource
import com.task.news.utils.WidgetUtils.setGone
import com.task.news.utils.WidgetUtils.setVisible

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {
    val adapter = NewsAdapter()
    var iteratorCount = 0
    override fun initView() {
    }

    override fun getContentView(): Int = R.layout.home_fragment

    override fun initializeViewModel() {
        val viewModel: HomeViewModel by viewModels()
        baseViewModel = viewModel
    }
    override fun onClick(v: View?) {
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        bindHeadlineNewsData()
        bindAllArticles()
    }

    private fun initNewsAdapter(recyclerView: RecyclerView , itemList: MutableList<Article>){
        recyclerView.layoutManager = LinearLayoutManager(context ,  LinearLayoutManager.VERTICAL , false)
        adapter.apply {
            submitMyList(itemList, favoriteClickCallback , articleClickCallback)
        }
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
    private fun bindHeadlineNewsData() {
        lifecycleScope.launchWhenStarted {
            baseViewModel?.headlineNews?.collect {
                when (it) {
                    is LiveDataResource.Success -> {
                        Timber.e("Size is ${it.data?.articles?.size}")
                        baseViewBinding.newsRecycler.setVisible()
                        baseViewBinding.newsShimmer.setGone()
                        baseViewBinding.noNetworkLayout.root.setGone()
                        baseViewBinding.noDataLayout.root.setGone()
                        baseViewBinding.serverErrorLayout.root.setGone()
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

                    else -> {
                    }
                }
            }
        }
    }

    private fun bindAllArticles() {
        lifecycleScope.launchWhenStarted {
            baseViewModel?.allArticle?.collect {
                when (it) {
                    is LiveDataResource.Success -> {
                        Timber.e("Success ${it.data}")
                        it.data?.let { list ->
                                initNewsAdapter(baseViewBinding.newsRecycler, list)
                        }
                    }
                    else -> {
                        adapter.notifyDataSetChanged()
                    }

                }
            }
        }
    }}