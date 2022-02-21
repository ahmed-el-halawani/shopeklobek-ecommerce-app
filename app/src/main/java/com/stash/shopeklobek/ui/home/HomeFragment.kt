package com.stash.shopeklobek.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.R
import com.stash.shopeklobek.databinding.FragmentHomeBinding
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.utils.NavigationExtension.setupWithNavController2

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavBar()



    }


    private fun setupBottomNavBar() {
        val navHostFragment =
            childFragmentManager.findFragmentById(binding.newsNavHostFragment.id) as NavHostFragment

        binding.bottomNavigationView
            .setupWithNavController2(
                navHostFragment.navController,
                mapOf(
//                    Pair(
//                        R.id.favoriteScreen,
//                        listOf(
//                            R.id.favoriteScreen,
//                            R.id.locationPreviewFragment
//                        )
//                    ),
//                    Pair(
//                        R.id.settingsFragment,
//                        listOf(
//                            R.id.settingsFragment,
//                            R.id.mapsFragment
//                        )
//                    )
                )
            )
    }

}