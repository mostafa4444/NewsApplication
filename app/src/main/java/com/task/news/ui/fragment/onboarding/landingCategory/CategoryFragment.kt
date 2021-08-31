package com.task.news.ui.fragment.onboarding.landingCategory

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.news.R
import com.task.news.base.BaseFragment
import com.task.news.databinding.CategoryFragmentBinding
import com.task.news.model.prefsModel.CategoryModel
import com.task.news.model.prefsModel.FilterModel
import com.task.news.ui.fragment.onboarding.landingCategory.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CategoryFragment : BaseFragment<CategoryViewModel, CategoryFragmentBinding>() {
    lateinit var adapter: CategoryAdapter
    private val args: CategoryFragmentArgs by navArgs()
    override fun initView() {
        initClick()
        adapter = CategoryAdapter()
        initCountryAdapter(baseViewBinding.categoryRecycler)
    }
    override fun getContentView(): Int = R.layout.category_fragment

    override fun initializeViewModel() {
        val viewModel: CategoryViewModel by viewModels()
        baseViewModel = viewModel
    }

    override fun onClick(v: View?) {
        when(v){
            baseViewBinding.toHomeBtn->{
                if (adapter.getSelectedList().size != 3){
                    Toast.makeText(context , getString(R.string.complete_categories) , Toast.LENGTH_SHORT).show()
                }else{
                    baseViewModel?.saveFilterModel_saveSelectionState(FilterModel(categories = adapter.getSelectedList() , args.countryCode))
                    findNavController().navigate(R.id.action_categoryFragment_to_homeFragment)
                }
                Timber.e("Selected ${adapter.getSelectedList()}")
            }
        }
    }

    private fun initClick(){
        baseViewBinding.toHomeBtn.setOnClickListener(this)
    }


    private fun initCountryAdapter(recyclerView: RecyclerView){
        val categoryModels = arrayOf(
                CategoryModel("Entertainment" , icon = R.drawable.ic_entertainment) ,
                CategoryModel("General" ,icon = R.drawable.ic_general) ,
                CategoryModel("Sports",icon = R.drawable.ic_sport),
                CategoryModel("Health",icon = R.drawable.ic_healthcare),
                CategoryModel("Science",icon = R.drawable.ic_science),
                CategoryModel("Business",icon = R.drawable.ic_business),
                CategoryModel("Technology",icon = R.drawable.ic_technology)
        )
        recyclerView.layoutManager = GridLayoutManager(context , 2 , GridLayoutManager.VERTICAL, false)
        adapter.apply {
            submitMyList(categoryModels.toMutableList())
        }
        recyclerView.adapter = adapter
    }


}