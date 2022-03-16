package com.stash.shopeklobek.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.stash.shopeklobek.databinding.FragmentOrdersBinding
import com.stash.shopeklobek.model.entities.retroOrder.Order
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.home.favorites.AdapterFavorite
import com.stash.shopeklobek.ui.home.favorites.FavoritesFragmentDirections
import com.stash.shopeklobek.utils.Constants

class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {

    private lateinit var adapterOrder: AdapterOrder
    private lateinit var order: ArrayList<Order>
    private val profileViewModel by lazy {
        ProfileViewModel.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bandle = Bundle().apply {
            putString("id", "")
        }


        adapterOrder = AdapterOrder(ArrayList()){
            val action =
                OrdersFragmentDirections.actionOrdersFragmentToNavOrderDetails(it)
            findNavController().navigate(action)
        }

        binding.reOrderList.layoutManager =
            LinearLayoutManager(context)
        binding?.reOrderList?.adapter = adapterOrder


        //        lifecycleScope.launch {
        //            when(val res=)
        //            {
        //                is Either.Error -> {
        //                    Log.d("onViewCreated", "nullll : error code" +res.errorCode+" : message :"+res
        //                        .message)
        //                }
        //                is Either.Success -> {
        //
        //                    Log.e("getOrdersProfile", "onViewCreated: "+res.data, )

        //
        profileViewModel.getOrders().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapterOrder.setOrders(it.reversed())
                binding.ivEmptyOrders.visibility = View.GONE
            } else {
                binding.ivEmptyOrders.visibility = View.VISIBLE
            }
        }

        //                }}
        //        }


    }
}