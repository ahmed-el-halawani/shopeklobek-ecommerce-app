package com.stash.shopeklobek.ui.checkout.shipping_addresses

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentShippingAddressBinding
import com.stash.shopeklobek.model.entities.Address
import com.stash.shopeklobek.model.entities.room.RoomCart
import com.stash.shopeklobek.ui.checkout.CheckoutBaseFragment
import com.stash.shopeklobek.utils.ViewHelpers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        shippingAddressViewModel.loading.observe(viewLifecycleOwner){
            if(it)
                showLoading()
            else
                hideLoading()
        }

        shippingAddressViewModel.getCustomerShippingAddresses()

        shippingAddressViewModel.addressesLiveData.observe(viewLifecycleOwner) {customer->
            val addresses = customer.addresses?.filter { !it.default }
            historyOfAddressesAdapter.differ.submitList(addresses ?: emptyList<Address>())

            binding.cvCurrentLocation.run {
                val firstAddress = customer.getDefaultOrFirst()
                if (firstAddress == null)
                    visibility = View.GONE
                else {
                    visibility = View.VISIBLE
                    address = firstAddress.address1
                    address2 = firstAddress.address
                    setOnClickListener { onAddressClicked(firstAddress) }
                    setImageFromUrl(firstAddress.getLatLng())
                }
                refresh()
            }

        }

        binding.apply {
            cvAddAddress.btn.setOnClickListener {
                println("create address")
                findNavController().navigate(R.id
                    .action_shippingAddressesFragment_to_addAddressFragment,Bundle().apply {
                    putBoolean("isDefault",false) })
            }
        }


        ItemTouchHelper(ViewHelpers.SwipeToRemove { position ->
            val address = historyOfAddressesAdapter.differ.currentList[position]

            deleteAddressDialog(address,position)

        }).attachToRecyclerView(binding.rvPastLocation)

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

    private fun deleteAddressDialog(address: Address,position:Int) {
        AlertDialog.Builder(context).apply {
            setNegativeButton("No") { d, _ ->
                historyOfAddressesAdapter.notifyItemChanged(position)
                d.dismiss()
            }
            setPositiveButton("yes") { d, _ ->
                lifecycleScope.launch {
                    shippingAddressViewModel.productRepo.deleteAddress(address.id?:0)
                    withContext(Dispatchers.Main){
                        shippingAddressViewModel.getCustomerShippingAddresses()
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.product_deleted),
                            Toast.LENGTH_SHORT
                        ).show()
                        d.dismiss()
                    }
                }
            }

            setTitle(getString(R.string.do_u_want_remove_address))
        }.create().show()
    }

}