package com.task.news.ui.fragment.webFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.task.news.R
import com.task.news.base.BaseFragment
import com.task.news.databinding.WebFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebFragment : BaseFragment<WebViewModel , WebFragmentBinding>() {

    private val args: WebFragmentArgs by navArgs()

    override fun initView() {
        baseViewBinding.newsWebView.apply {
            webViewClient = WebViewClient()
            loadUrl(args.url)
        }
    }

    override fun getContentView(): Int = R.layout.web_fragment

    override fun initializeViewModel() {
    }

    override fun onClick(v: View?) {
    }

}