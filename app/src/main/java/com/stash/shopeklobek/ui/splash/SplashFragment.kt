package com.stash.shopeklobek.ui.splash

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.stash.shopeklobek.databinding.FragmentHomeBinding
import com.stash.shopeklobek.databinding.FragmentSplashBinding
import com.stash.shopeklobek.ui.BaseFragment

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().popBackStack()
    }
}