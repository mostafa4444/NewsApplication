package com.task.news.ui.fragment.onboarding.landingCountry

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.news.R
import com.task.news.base.BaseFragment
import com.task.news.databinding.CountryFragmentBinding
import com.task.news.model.prefsModel.CountryModel
import com.task.news.ui.fragment.onboarding.landingCountry.adapter.CountryAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CountryFragment : BaseFragment<CountryViewModel, CountryFragmentBinding>() {

    lateinit var adapter: CountryAdapter

    override fun initView() {
        initClick()
        adapter = CountryAdapter()
        initCountryAdapter(baseViewBinding.countryRecycler)
    }
    override fun getContentView(): Int = R.layout.country_fragment

    override fun initializeViewModel() {
        val viewModel: CountryViewModel by viewModels()
        baseViewModel = viewModel
    }

    private fun initClick(){
        baseViewBinding.toCategoryBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            baseViewBinding.toCategoryBtn->{
                adapter.getCheckedCountry()?.let {
                    val direction = CountryFragmentDirections.actionCountryFragmentToCategoryFragment(it.code)
                    findNavController().navigate(direction)
                }
            }
        }
    }

    private fun initCountryAdapter(recyclerView: RecyclerView){
        val countriesModels = arrayOf(CountryModel("eg" , icon = R.drawable.ic_eg) ,
                CountryModel("fr" , icon =R.drawable.ic_fr) ,
                CountryModel("cz" , icon =R.drawable.ic_cz) ,
                CountryModel("us" , icon =R.drawable.ic_us))
        recyclerView.layoutManager = GridLayoutManager(context , 2,GridLayoutManager.VERTICAL , false)
        adapter.apply {
            submitMyList(countriesModels.toMutableList())
        }
        recyclerView.adapter = adapter
    }












}