package com.stash.shopeklobek.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentHomeBinding
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.MainActivity
import com.stash.shopeklobek.utils.NavigationExtension.setupWithNavController2

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    val navHostFragment by lazy {
        childFragmentManager.findFragmentById(binding.homeNavHostFragment.id) as NavHostFragment
    }

    val navController by lazy {
        navHostFragment.navController
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.onBackNavController = navController

        setupBottomNavBar()
    }

    private fun setupBottomNavBar() {
        setupActionBarWithNavController(mainActivity, navController, mainActivity.bottomNavAppBarConfiguration)


        binding.bottomNavigationView
            .setupWithNavController2(
                navController,
                mapOf(
                    Pair(
                        R.id.cartFragment,
                        listOf(
                            R.id.nav_login,
                            R.id.shappingAddressesFragment,
                        ),
                    ),
                    Pair(
                        R.id.categoriesFragment,
                        listOf(
                            R.id.product_details_fragment
                        ),
                    ),
                )
            )
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.onBackNavController = null
    }

}