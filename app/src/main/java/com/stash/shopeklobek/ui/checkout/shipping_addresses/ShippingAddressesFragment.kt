package com.stash.shopeklobek.ui.checkout.shipping_addresses

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentShippingAddressBinding
import com.stash.shopeklobek.ui.checkout.CheckoutBaseFragment

class ShippingAddressesFragment : CheckoutBaseFragment<FragmentShippingAddressBinding>(FragmentShippingAddressBinding::inflate) {

    private val historyOfAddressessAdapter by lazy {
        HistoryOfAddressesAdapter().apply {
        }
    }

    val shippingAddressViewModel by lazy {
        ShippingAddressViewModel.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()



        shippingAddressViewModel.productRepo.getSettingsLiveData().observe(viewLifecycleOwner) {
            println("settings form view model")
            println(it)
        }


        binding.apply {
            cvCurrentLocation.setOnClickListener {
                println("here")
                viewmodel.pageIndexLiveData.postValue(1)
                findNavController().navigate(R.id.action_shippingAddressesFragment_to_paymentMethod)
            }

            cvAddAddress.root.setOnClickListener {
                println("here")
            }


//            btnProceedToCheckout.setOnClickListener{
//
//            }


        }
    }

    private fun setupRecycleView() {
        binding.rvPastLocation.apply {
            adapter = historyOfAddressessAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }



}