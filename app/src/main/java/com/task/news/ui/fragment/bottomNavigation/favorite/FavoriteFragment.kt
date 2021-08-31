package com.task.news.ui.fragment.bottomNavigation.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.news.R
import com.task.news.base.BaseFragment
import com.task.news.databinding.CountryFragmentBinding
import com.task.news.databinding.FavoriteFragmentBinding
import com.task.news.model.prefsModel.CountryModel
import com.task.news.ui.fragment.onboarding.landingCountry.CountryFragmentDirections
import com.task.news.ui.fragment.onboarding.landingCountry.CountryViewModel
import com.task.news.ui.fragment.onboarding.landingCountry.adapter.CountryAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FavoriteViewModel, FavoriteFragmentBinding>() {

    override fun initView() {
    }
    override fun getContentView(): Int = R.layout.favorite_fragment

    override fun initializeViewModel() {
        val viewModel: FavoriteViewModel by viewModels()
        baseViewModel = viewModel
    }

    override fun onClick(v: View?) {
    }


}