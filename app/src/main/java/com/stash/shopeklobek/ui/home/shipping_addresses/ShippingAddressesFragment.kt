package com.stash.shopeklobek.ui.home.shipping_addresses

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.stash.shopeklobek.databinding.FragmentShippingAddressBinding
import com.stash.shopeklobek.ui.BaseFragment

class ShippingAddressesFragment : BaseFragment<FragmentShippingAddressBinding>(FragmentShippingAddressBinding::inflate) {

    private val historyOfAddressessAdapter by lazy {
        HistoryOfAddressesAdapter().apply {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()


        binding.apply {
            addressCardView.setOnClickListener {
                println("here")
            }


//            btnProceedToCheckout.setOnClickListener{
//
//            }


        }
    }

    private fun setupRecycleView() {
        binding.recyclerView.apply {
            adapter = historyOfAddressessAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }



}