package com.stash.shopeklobek.ui.checkout.shipping_addresses

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentShippingAddressBinding
import com.stash.shopeklobek.model.entities.Address
import com.stash.shopeklobek.ui.checkout.CheckoutBaseFragment

class ShippingAddressesFragment : CheckoutBaseFragment<FragmentShippingAddressBinding>(FragmentShippingAddressBinding::inflate) {

    private val historyOfAddressesAdapter by lazy {
        HistoryOfAddressesAdapter().apply {
            setOnItemClickListener(::onAddressClicked)
        }
    }

    private val shippingAddressViewModel by lazy {
        ShippingAddressViewModel.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()

        shippingAddressViewModel.productRepo.getSettingsLiveData().observe(viewLifecycleOwner) {
            println("settings form view model")
            println(it)
        }

        shippingAddressViewModel.getCustomerShippingAddresses()

        shippingAddressViewModel.addressesLiveData.observe(viewLifecycleOwner) {
            historyOfAddressesAdapter.differ.submitList(it.addresses ?: emptyList<Address>())

            val firstAddress = it.addresses?.first()
            binding.cvCurrentLocation.run {
                if (firstAddress == null)
                    visibility = View.GONE
                else {
                    visibility = View.VISIBLE
                    address = firstAddress.generateAddressLine()
                    setOnClickListener { onAddressClicked(firstAddress) }
                }
            }

        }

        binding.apply {

            cvAddAddress.btn.setOnClickListener {
                println("create address")
                findNavController().navigate(R.id.action_shippingAddressesFragment_to_addAddressFragment)
            }

        }
    }

    private fun setupRecycleView() {
        binding.rvPastLocation.apply {
            adapter = historyOfAddressesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun onAddressClicked(address: Address) {
        mainViewModel.pageIndexLiveData.postValue(1)
        mainViewModel.selectedAddress = address
        findNavController().navigate(R.id.action_shippingAddressesFragment_to_paymentMethod)
    }


}