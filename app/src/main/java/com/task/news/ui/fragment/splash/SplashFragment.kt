package com.task.news.ui.fragment.splash

import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.task.news.R
import com.task.news.base.BaseFragment
import com.task.news.databinding.SplashFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel , SplashFragmentBinding>() {
    override fun initView() {
        startSplash()
    }
    private fun startSplash() {
        val timer = object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
            }
        }
        timer.start()
        val myAnim: Animation = AnimationUtils.loadAnimation(context, R.anim.splash_logo_animation)
        baseViewBinding.splashLogo.startAnimation(myAnim)
    }
    override fun getContentView(): Int = R.layout.splash_fragment

    override fun initializeViewModel() {
    }

    override fun onClick(v: View?) {
    }


}