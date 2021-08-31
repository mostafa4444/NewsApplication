package com.task.news.ui.fragment.bottomNavigation.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.task.news.R
import com.task.news.base.BaseFragment
import com.task.news.databinding.ActivityMainBinding.inflate
import com.task.news.databinding.CountryFragmentBinding
import com.task.news.databinding.SearchFragmentBinding
import com.task.news.model.prefsModel.CategoryModel
import com.task.news.model.prefsModel.CountryModel
import com.task.news.model.response.news.Article
import com.task.news.ui.activity.MainActivity
import com.task.news.ui.fragment.bottomNavigation.home.adapter.NewsAdapter
import com.task.news.ui.fragment.onboarding.landingCountry.CountryFragmentDirections
import com.task.news.ui.fragment.onboarding.landingCountry.CountryViewModel
import com.task.news.ui.fragment.onboarding.landingCountry.adapter.CountryAdapter
import com.task.news.utils.AnimationUtils
import com.task.news.utils.LiveDataResource
import com.task.news.utils.WidgetUtils.setGone
import com.task.news.utils.WidgetUtils.setHintTextColor
import com.task.news.utils.WidgetUtils.setTextColor
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
                    baseViewModel?.fetchNews(baseViewBinding.searchEdt.text.toString() , searchCategory)
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
                    baseViewModel?.fetchNews(baseViewBinding?.searchEdt.text.toString() , chip.text.toString())
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


    private fun bindSearchData(){
        lifecycleScope.launchWhenStarted {
            baseViewModel?.searchNews?.collect{
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

    private fun initNewsAdapter(recyclerView: RecyclerView, itemList: MutableList<Article>){
        recyclerView.layoutManager = LinearLayoutManager(context ,  LinearLayoutManager.VERTICAL , false)
        val adapter = NewsAdapter().apply {
            submitMyList(itemList)
        }
        recyclerView.setVisible()
        recyclerView.adapter = adapter
        recyclerView.startLayoutAnimation()
    }




}