package com.stash.shopeklobek.ui.profile

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentProfileBinding
import com.stash.shopeklobek.model.entities.Order
import com.stash.shopeklobek.model.entities.room.RoomCart
import com.stash.shopeklobek.model.entities.room.RoomFavorite
import com.stash.shopeklobek.model.entities.room.RoomOrder
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.home.favorites.AdapterFavorite
import com.stash.shopeklobek.utils.Constants

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

        activity?.let {
            val sharedPreferences: SharedPreferences = it.getSharedPreferences(
                "sharedPrefFile",
                Context.MODE_PRIVATE
            )

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt(Constants.ONCLICK_PROFILE, 1)
            editor.apply()
            editor.commit()

        }

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
        }else
        {
            // get data from room
            when (val res = profileViewModel.getFavorites()) {
                is Either.Error -> {
                    TODO()
                }
                is Either.Success -> {
                    res.data.observe(viewLifecycleOwner) {
                        if (!it.isEmpty()) {
                            binding.allFavorites.text="${it.size}"
                            favorite.add(it[0])
                            if (it.size > 1) {
                                favorite.add(it[1])
                                adapterFavorite.setFavorite(favorite)
                            }
                            adapterFavorite.setFavorite(favorite)
                        }else{
                            binding.allFavorites.text="0"

                        }
                    }
                }
            }

            profileViewModel.getCart()
            profileViewModel.cartLiveData.observe(viewLifecycleOwner) { roomCarts ->
                if (roomCarts.isEmpty())
                 else
                    binding.allCart.text="${roomCarts.size}"
            }
            when (val res = profileViewModel.getOrders()) {
                is Either.Error -> {
                }
                is Either.Success -> {
                    res.data.observe(viewLifecycleOwner) {
                        if (!it.isEmpty()) {
                            binding.allOrders.text="${it.size}"
                            order.add(it[0])
                            adapterOrder.setOrders(order)
                        }else{
                            binding.allOrders.text="0"

                        }
                    }
                }
            }
            val str = it.customer!!.firstName
            val delim = " "

            val list = str!!.split(delim)

            println(list)
            binding.tvFirstName.text= it.customer!!.firstName

            binding.textEmail.text=it.customer!!.email
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