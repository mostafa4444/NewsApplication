package com.task.news.ui.fragment.home

import android.util.TypedValue
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.task.news.R
import com.task.news.base.BaseFragment
import com.task.news.databinding.HomeFragmentBinding
import com.task.news.model.CategoryModel
import com.task.news.ui.activity.MainActivity
import com.task.news.utils.AnimationUtils.slideFromRightToLeft
import com.task.news.utils.WidgetUtils.setHintTextColor
import com.task.news.utils.WidgetUtils.setTextColor
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, HomeFragmentBinding>() {
    override fun initView() {
        (activity as MainActivity).setSupportActionBar(baseViewBinding.topAppBar)
        setHasOptionsMenu(true)
        setupChipsWithFavorite()
    }

    private fun setupChipsWithFavorite(){
        baseViewModel?.getMyFilterModel()?.let {
            setCategoryChips(it.categories)
        }
    }

    override fun getContentView(): Int = R.layout.home_fragment

    override fun initializeViewModel() {
        val viewModel: HomeViewModel by viewModels()
        baseViewModel = viewModel
    }

    override fun onClick(v: View?) {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                slideFromRightToLeft(baseViewBinding.topAppBar)
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


}