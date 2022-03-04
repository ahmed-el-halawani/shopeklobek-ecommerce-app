package com.stash.shopeklobek.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentProfileBinding
import com.stash.shopeklobek.model.ModelFavorite
import com.stash.shopeklobek.model.entities.Order
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.home.favorites.AdapterFavorite

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private lateinit var adapterOrder: AdapterOrder
    private lateinit var order: ArrayList<Order>
    private lateinit var adapterFavorite: AdapterFavorite

    private val profileViewModel by lazy{
        ProfileViewModel.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        order=ArrayList<Order>()
        order.add(Order("55.5$","done","2021-04-10 10:28:21.052"))
        adapterOrder = AdapterOrder(ArrayList())

        binding.reOrderList.layoutManager =
            LinearLayoutManager(context)
        binding.reOrderList.adapter = adapterOrder

        adapterOrder.setOrders(order)

        adapterFavorite = AdapterFavorite(ArrayList())

        binding.reFavorite.layoutManager =
            GridLayoutManager(context, 2)
        binding?.reFavorite?.adapter = adapterFavorite
        var arrayList=ArrayList<ModelFavorite>()
        arrayList.add(ModelFavorite("Sneakers","https://assets.brantu.com/product/3987777-71619/1000x1500/1640123440076-3987777-71619-0-3.jpeg","300.30$"))
        arrayList.add(ModelFavorite("Ankle Boot","https://assets.brantu.com/product/7783897-42287/1000x1500/1637228503727-7783897-42287-0-3.jpeg","300.30$"))
        adapterFavorite.setFavorite(arrayList)

        binding.tvMoreFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_more_favorite)

        }
        binding.tvMoreOrder.setOnClickListener {

            findNavController().navigate(R.id.action_more_order)

        }
    }
}