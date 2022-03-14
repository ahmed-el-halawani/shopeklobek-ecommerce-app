package com.stash.shopeklobek.ui.profile

import android.app.Application
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentProfileBinding
import com.stash.shopeklobek.model.entities.room.RoomFavorite
import com.stash.shopeklobek.model.entities.room.RoomOrder
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.home.favorites.AdapterFavorite

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private lateinit var adapterOrder: AdapterOrder
    private lateinit var order: ArrayList<RoomOrder>
    private lateinit var favorite: ArrayList<RoomFavorite>

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



        order = ArrayList<RoomOrder>()
        favorite = ArrayList<RoomFavorite>()
        adapterOrder = AdapterOrder(ArrayList())

        binding.reOrderList.layoutManager =
            LinearLayoutManager(context)
        binding.reOrderList.adapter = adapterOrder
        profileViewModel.productRepo.getSettingsLiveData().observe(viewLifecycleOwner) {

            if (it.customer == null) {
                findNavController().navigate(R.id.action_nav_profile_to_loogin)
            } else {
                // get data from room
                when (val res = profileViewModel.getFavorites()) {
                    is Either.Error -> {
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                    }
                    is Either.Success -> {
                        res.data.observe(viewLifecycleOwner) {
                                binding.allFavorites.text = "${it.size}"
                                adapterFavorite.setFavorite(it.take(4))
                        }
                    }
                }

                profileViewModel.getCart()
                profileViewModel.cartLiveData.observe(viewLifecycleOwner) { roomCarts ->
                    binding.allCart.text = "${roomCarts.size}"
                }

                profileViewModel.getOrders().observe(viewLifecycleOwner) {
                    binding.allOrders.text = it.size.toString()
                    adapterOrder.setOrders(it.take(2))
                }


                val str = it.customer!!.firstName
                val delim = " "

                val list = str!!.split(delim)

                println(list)
                binding.tvFirstName.text = list[0]
                if (list.size > 1)
                    binding.tvEndName.text = list[1]
                binding.textEmail.text = it.customer!!.email
            }


        }

        adapterFavorite = AdapterFavorite(ArrayList(), null)

        binding.reFavorite.layoutManager =
            GridLayoutManager(context, 2)
        binding?.reFavorite?.adapter = adapterFavorite


        binding.tvMoreFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_more_favorite)

        }
        binding.tvMoreOrder.setOnClickListener {

            findNavController().navigate(R.id.action_more_order)

        }
    }

}