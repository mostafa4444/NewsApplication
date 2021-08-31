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

    override fun initView() {
        (activity as MainActivity).setSupportActionBar(baseViewBinding.topAppBar)
        setHasOptionsMenu(true)
        setupChipsWithFavorite()
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
    }
    private fun setupChipsWithFavorite(){
        baseViewModel?.getMyFilterModel()?.let {
            setCategoryChips(it.categories)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                AnimationUtils.slideFromRightToLeft(baseViewBinding.topAppBar)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)
        val menuItem: MenuItem = menu.findItem(R.id.search)
        val searchView = menuItem.actionView as SearchView
        setupSearchBar(searchView)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupSearchBar(searchView: SearchView){
        searchView.setTextColor(ContextCompat.getColor(context?.applicationContext!!, R.color.black))
        searchView.setHintTextColor(ContextCompat.getColor(context?.applicationContext!!, R.color.black))
        searchView.background = ContextCompat.getDrawable(context?.applicationContext!!, R.drawable.custom_search_view)
        searchView.setIconifiedByDefault(false)
        searchView.isIconified = false
        searchView.queryHint = getString(R.string.search_txt)
        val queryTextListener: SearchView.OnQueryTextListener =
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        Timber.e("Query Text is $query")
                        baseViewModel?.fetchNews(query)
                        searchView.clearFocus()
                        return true
                    }
                    override fun onQueryTextChange(newText: String): Boolean {
                        return true
                    }
                }
        searchView.setOnQueryTextListener(queryTextListener)
    }


    private fun setCategoryChips(categories: List<CategoryModel>) {
        categories.forEach{ category->
            val mChip = this.layoutInflater.inflate(R.layout.single_chip_item, null, false) as Chip
            mChip.id = View.generateViewId()
            mChip.text = category.name
            val paddingDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10f,
                    resources.displayMetrics
            ).toInt()
            mChip.setPadding(paddingDp, 0, paddingDp, 0)
            mChip.setOnCheckedChangeListener { _, _ ->
                Timber.e("Selected is ${category.name}")
            }
            baseViewBinding.choicesChip.addView(mChip)
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