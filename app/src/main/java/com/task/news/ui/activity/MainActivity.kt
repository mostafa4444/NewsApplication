package com.task.news.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.task.news.R
import com.task.news.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
    private var navHost : NavHostFragment ?= null
    private val bottomNavDestinationIds = setOf(
        R.id.homeFragment,
        R.id.searchFragment,
        R.id.favoriteFragment,
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initNavHost()
        setUpBottomNavigationWithController()
        setUpVisibilityBottomNavigation()
    }

    private fun initNavHost(){
        navHost = supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        navController = navHost!!.navController
    }

    private fun setUpBottomNavigationWithController(){
        binding.navigationBottom.background = null
        NavigationUI.setupWithNavController(binding.navigationBottom, navController!!)
        binding.navigationBottom.setupWithNavController(navController!!)
    }

    private fun setUpVisibilityBottomNavigation(){
        navController?.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in bottomNavDestinationIds) {
                binding.bottomAppBar.visibility = View.VISIBLE
            }else{
                binding.bottomAppBar.visibility = View.GONE
            }
        }
    }

}