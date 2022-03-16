package com.stash.shopeklobek.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentHomeBinding
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.utils.NavigationExtension.setupWithNavController2

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    val navHostFragment by lazy {
        childFragmentManager.findFragmentById(binding.homeNavHostFragment.id) as NavHostFragment
    }

    val navController by lazy {
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.onBackNavController = navController

        setupBottomNavBar()

        mainActivity.viewmodel.productRepo.getCart().observe(viewLifecycleOwner){
            binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment).number = it.size
        }

    }

    private fun setupBottomNavBar() {
        setupActionBarWithNavController(
            mainActivity,
            navController,
            mainActivity.bottomNavAppBarConfiguration
        )

        binding.bottomNavigationView.setupWithNavController2(navController)



    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity.onBackNavController = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main,menu)
    }


}