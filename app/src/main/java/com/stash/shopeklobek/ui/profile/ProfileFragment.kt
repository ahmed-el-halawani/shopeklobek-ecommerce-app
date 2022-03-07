package com.stash.shopeklobek.ui.profile

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentProfileBinding
import com.stash.shopeklobek.model.entities.Order
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.home.favorites.AdapterFavorite

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private lateinit var adapterOrder: AdapterOrder
    private lateinit var order: ArrayList<Order>
    private lateinit var adapterFavorite: AdapterFavorite

    private val profileViewModel by lazy {
        ProfileViewModel.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnSighnout.setOnClickListener {
            SettingsPreferences.getInstance(context?.applicationContext as Application).update {
                it.apply {
                    customer = null
                }
            }
        }


        order = ArrayList<Order>()
        order.add(Order("55.5$", "done", "2021-04-10 10:28:21.052"))
        adapterOrder = AdapterOrder(ArrayList())

        binding.reOrderList.layoutManager =
            LinearLayoutManager(context)
        binding.reOrderList.adapter = adapterOrder



        adapterFavorite = AdapterFavorite(ArrayList(), null)

        binding.reFavorite.layoutManager =
            GridLayoutManager(context, 2)
        binding?.reFavorite?.adapter = adapterFavorite
        // get data from room
        profileViewModel.getFavorites()
        profileViewModel.getOrders()
        profileViewModel.favorites.observe(viewLifecycleOwner, Observer {
            if (it != null)
                adapterFavorite.setFavorite(it)

        })

        profileViewModel.orders.observe(viewLifecycleOwner, Observer {
            if (it != null)
                adapterOrder.setOrders(it)


        })

        binding.tvMoreFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_more_favorite)

        }
        binding.tvMoreOrder.setOnClickListener {

            findNavController().navigate(R.id.action_more_order)

        }
    }

}