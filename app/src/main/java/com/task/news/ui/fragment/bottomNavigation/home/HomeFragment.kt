package com.task.news.ui.fragment.bottomNavigation.home

import android.content.res.ColorStateList
import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.news.R
import com.task.news.base.BaseFragment
import com.task.news.databinding.HomeFragmentBinding
import com.task.news.model.prefsModel.CategoryModel
import com.task.news.model.response.news.Article
import com.task.news.ui.fragment.bottomNavigation.home.adapter.CategoryClickEvent
import com.task.news.ui.fragment.bottomNavigation.home.adapter.NewsAdapter
import com.task.news.ui.fragment.bottomNavigation.home.adapter.SelectedCategoryRecycler
import com.task.news.utils.LiveDataResource
import com.task.news.utils.WidgetUtils.setGone
import com.task.news.utils.WidgetUtils.setVisible

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {
    var def : ColorStateList?= null

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
            submitMyList(itemList)
        }
        recyclerView.setVisible()
        recyclerView.adapter = adapter
        recyclerView.startLayoutAnimation()
    }

    val categoryClickEventImpl = object : CategoryClickEvent{
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
                        baseViewBinding.newsRecycler
                        it.data?.articles?.let { data->
                            baseViewBinding.newsShimmer.root.setGone()
                            initNewsAdapter(baseViewBinding.newsRecycler , data.toMutableList())
                        }
                    }
                    is LiveDataResource.Loading -> {
                        baseViewBinding.newsShimmer.root.setVisible()
                        baseViewBinding.newsRecycler.setGone()
                    }
                    is LiveDataResource.Error -> {
                        baseViewBinding.newsShimmer.root.setGone()
                        baseViewBinding.newsRecycler.setGone()
                    }else->{}
                }
            }
        }
    }


}