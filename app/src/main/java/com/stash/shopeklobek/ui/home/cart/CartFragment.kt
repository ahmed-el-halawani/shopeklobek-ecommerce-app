package com.stash.shopeklobek.ui.home.cart

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentCartBinding
import com.stash.shopeklobek.databinding.FragmentHomeBinding
import com.stash.shopeklobek.ui.BaseFragment

class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGo.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_nav_login)
        }
    }
}