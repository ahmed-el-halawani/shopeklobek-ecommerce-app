package com.stash.shopeklobek.ui.home

import android.os.Bundle
import android.view.*
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.onBackNavController = navController

        setupBottomNavBar()



    }

    private fun setupBottomNavBar() {
        binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment).isVisible = false
        mainActivity.viewmodel.productRepo.getSettingsLiveData().observe(viewLifecycleOwner){settings->
            mainActivity.viewmodel.productRepo.getCart().observe(viewLifecycleOwner) {
                if (settings.customer != null) {
                    binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment).number = it.size
                    binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment).isVisible =
                        it.isNotEmpty()
                } else {
                    binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment).isVisible = false
                }
            }
        }

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
        inflater.inflate(R.menu.main, menu)
    }


}