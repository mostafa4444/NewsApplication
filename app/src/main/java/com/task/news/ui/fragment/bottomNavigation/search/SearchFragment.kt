package com.task.news.ui.fragment.bottomNavigation.search

import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.task.news.R
import com.task.news.base.BaseFragment
import com.task.news.databinding.SearchFragmentBinding
import com.task.news.model.response.news.Article
import com.task.news.ui.fragment.bottomNavigation.home.HomeFragmentDirections
import com.task.news.ui.fragment.bottomNavigation.home.HomeViewModel
import com.task.news.ui.fragment.bottomNavigation.home.adapter.NewsAdapter
import com.task.news.ui.fragment.bottomNavigation.home.adapter.clickListeners.ArticleClickEvent
import com.task.news.ui.fragment.bottomNavigation.home.adapter.clickListeners.NewsFavoriteClick
import com.task.news.utils.LiveDataResource
import com.task.news.utils.WidgetUtils.setGone
import com.task.news.utils.WidgetUtils.setVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchViewModel, SearchFragmentBinding>() {

    var searchCategory = ""

    override fun initView() {
        baseViewBinding.searchBtn.setOnClickListener(this)
        submitCategoriesChips()
    }
    override fun getContentView(): Int = R.layout.search_fragment

    override fun initializeViewModel() {
        val viewModel: SearchViewModel by viewModels()
        baseViewModel = viewModel
    }

    override fun subscribeLiveData() {
        super.subscribeLiveData()
        bindSearchData()
    }

    override fun onClick(v: View?) {
        when(v){
            baseViewBinding.searchBtn->{
                if (baseViewBinding.searchEdt.text.toString().isNotEmpty() || searchCategory.isNotEmpty()){
                    baseViewModel?.searchNews(baseViewBinding.searchEdt.text.toString() , searchCategory)
                }else{

                }
            }
        }
    }

    private fun submitCategoriesChips(){
        val categories = baseViewModel?.getMyFilterModel()?.categories
        categories?.forEach {
            val chip = this.layoutInflater.inflate(R.layout.single_chip_item, baseViewBinding.categoryChip, false) as Chip
            chip.text = (it.name)
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    baseViewModel?.searchNews(baseViewBinding?.searchEdt.text.toString() , chip.text.toString())
                    searchCategory = chip.text.toString()
                    Timber.e("Clicked ${chip.text}")
                }else{
                    searchCategory = ""
                    Timber.e("Deselect ${chip.text}")
                }

            }
            baseViewBinding.categoryChip.addView(chip)
        }
    }
    private val favoriteClickCallback = object : NewsFavoriteClick {
        override fun newClickFavorite(article: Article , position: Int) {
            baseViewModel?.insertArticleToDatabase(article)
        }

    }


    private fun bindSearchData(){
        lifecycleScope.launchWhenStarted {
            baseViewModel?.searchNews?.collect{
                when(it){
                    is LiveDataResource.Success -> {
                        Timber.e("Size is ${it.data?.articles?.size}")
                        baseViewBinding.newsRecycler
                        it.data?.articles?.let { data->
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
                    else->{
                        baseViewBinding.newsShimmer.setGone()
                        baseViewBinding.newsRecycler.setGone()
                        baseViewBinding.noNetworkLayout.root.setGone()
                        baseViewBinding.noDataLayout.root.setVisible()
                        baseViewBinding.serverErrorLayout.root.setGone()
                    }
                }
            }
        }
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

    private val articleClickCallback = object : ArticleClickEvent {
        override fun articleClickEvent(article: Article) {
            val direction = SearchFragmentDirections.actionSearchFragmentToWebFragment(article.url.toString())
            findNavController().navigate(direction)
        }
    }




}