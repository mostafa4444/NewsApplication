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
import com.task.news.model.response.news.Article
import com.task.news.ui.fragment.bottomNavigation.home.adapter.NewsAdapter
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
        def = baseViewBinding.categoryTabTwo.textColors
        bindCategories()
        initClicks()
    }

    override fun getContentView(): Int = R.layout.home_fragment

    override fun initializeViewModel() {
        val viewModel: HomeViewModel by viewModels()
        baseViewModel = viewModel
    }

    private fun bindCategories(){
        baseViewModel?.getMyFilterModel()?.categories?.let {
            val tabsArray = arrayListOf(baseViewBinding.categoryTabOne , baseViewBinding.categoryTabTwo , baseViewBinding.categoryTabThree)
            for (i in tabsArray.indices){
                tabsArray[i].text = it[i].name
            }
        }

    }

    private fun initClicks(){
        baseViewBinding.categoryTabOne.setOnClickListener(this)
        baseViewBinding.categoryTabTwo.setOnClickListener(this)
        baseViewBinding.categoryTabThree.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            baseViewBinding.categoryTabOne->{
                baseViewBinding.selectedTab.animate().x(0F).duration = 100
                baseViewBinding.categoryTabOne.setTextColor(ContextCompat.getColor(context?.applicationContext!! , R.color.white))
                baseViewBinding.categoryTabTwo.setTextColor(def)
                baseViewBinding.categoryTabThree.setTextColor(def)
                baseViewModel?.cancelAndStartNewCall(0)
            }
            baseViewBinding.categoryTabThree->{
                val size = baseViewBinding.categoryTabTwo.width
                baseViewBinding.selectedTab.animate().x(size.toFloat() * 2).duration = 100
                baseViewBinding.categoryTabThree.setTextColor(ContextCompat.getColor(context?.applicationContext!!, R.color.white))
                baseViewBinding.categoryTabTwo.setTextColor(def)
                baseViewBinding.categoryTabOne.setTextColor(def)
                baseViewModel?.cancelAndStartNewCall(2)
            }
            baseViewBinding.categoryTabTwo->{
                val size = baseViewBinding.categoryTabTwo.width
                baseViewBinding.selectedTab.animate().x(size.toFloat()).duration = 100
                baseViewBinding.categoryTabTwo.setTextColor(ContextCompat.getColor(context?.applicationContext!! , R.color.white))
                baseViewBinding.categoryTabOne.setTextColor(def)
                baseViewBinding.categoryTabThree.setTextColor(def)
                baseViewModel?.cancelAndStartNewCall(1)
            }
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

    private fun bindHeadlineNewsData(){
        lifecycleScope.launchWhenStarted {
            baseViewModel?.headlineNews?.collect{
                when(it){
                    is LiveDataResource.Success -> {
                        Timber.e("Size is ${it.data?.articles?.size}")
                        baseViewBinding.newsRecycler
                        it.data?.articles?.let { data->
                            baseViewBinding.newsShimmer.setGone()
                            initNewsAdapter(baseViewBinding.newsRecycler , data.toMutableList())
                        }
                    }
                    is LiveDataResource.Loading -> {
                        baseViewBinding.newsShimmer.setVisible()
                        baseViewBinding.newsRecycler.setGone()
                    }
                    is LiveDataResource.Error -> {
                        baseViewBinding.newsShimmer.setGone()
                        baseViewBinding.newsRecycler.setGone()
                    }else->{}
                }
            }
        }
    }






}