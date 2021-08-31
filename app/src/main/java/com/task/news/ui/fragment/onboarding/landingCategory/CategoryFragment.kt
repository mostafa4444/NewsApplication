package com.task.news.ui.fragment.onboarding.landingCategory

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
import com.task.news.R
import com.task.news.base.BaseFragment
import com.task.news.databinding.CategoryFragmentBinding
import com.task.news.databinding.SplashFragmentBinding
import com.task.news.ui.fragment.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<CategoryViewModel, CategoryFragmentBinding>() {
    override fun initView() {
    }
    override fun getContentView(): Int = R.layout.category_fragment

    override fun initializeViewModel() {
        val viewModel: CategoryViewModel by viewModels()
        baseViewModel = viewModel
    }

    override fun onClick(v: View?) {
    }


}