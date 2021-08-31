package com.task.news.ui.fragment.onboarding.landingCountry

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.news.R
import com.task.news.base.BaseFragment
import com.task.news.databinding.CategoryFragmentBinding
import com.task.news.databinding.CountryFragmentBinding
import com.task.news.databinding.SplashFragmentBinding
import com.task.news.model.CountryModel
import com.task.news.ui.fragment.onboarding.landingCategory.CategoryViewModel
import com.task.news.ui.fragment.onboarding.landingCountry.adapter.CountryAdapter
import com.task.news.ui.fragment.splash.SplashViewModel
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
                Timber.e("Selected is ${adapter.getCheckedCountry()}")
            }
        }
    }

    private fun initCountryAdapter(recyclerView: RecyclerView){
        val countriesModels = arrayOf(CountryModel("fr") , CountryModel("eg") , CountryModel("us"))
        recyclerView.layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)
        adapter.apply {
            submitMyList(countriesModels.toMutableList())
        }
        recyclerView.adapter = adapter
    }












}