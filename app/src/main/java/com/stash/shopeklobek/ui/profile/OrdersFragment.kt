package com.stash.shopeklobek.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentOrdersBinding
import com.stash.shopeklobek.databinding.FragmentProfileBinding
import com.stash.shopeklobek.model.ModelFavorite
import com.stash.shopeklobek.model.Order
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.home.favorites.AdapterFavorite

class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {

    private lateinit var adapterOrder: AdapterOrder
    private lateinit var order: ArrayList<Order>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        order=ArrayList<Order>()
        order.add(Order("55.5$","done","2021-04-10 10:28:21.052"))
        order.add(Order("88.5$","cancel","2021-04-10 10:28:21.052"))
        order.add(Order("550.5$","done","2021-04-10 10:28:21.052"))
        order.add(Order("55.5$","done","2021-04-10 10:28:21.052"))
        order.add(Order("88.5$","cancel","2021-04-10 10:28:21.052"))
        order.add(Order("550.5$","done","2021-04-10 10:28:21.052"))
        order.add(Order("55.5$","done","2021-04-10 10:28:21.052"))
        order.add(Order("88.5$","cancel","2021-04-10 10:28:21.052"))
        order.add(Order("550.5$","done","2021-04-10 10:28:21.052"))
        order.add(Order("55.5$","done","2021-04-10 10:28:21.052"))
        order.add(Order("88.5$","cancel","2021-04-10 10:28:21.052"))
        order.add(Order("550.5$","done","2021-04-10 10:28:21.052"))
        order.add(Order("55.5$","done","2021-04-10 10:28:21.052"))
        order.add(Order("88.5$","cancel","2021-04-10 10:28:21.052"))
        order.add(Order("550.5$","done","2021-04-10 10:28:21.052"))

        adapterOrder = AdapterOrder(ArrayList())

        binding.reOrderList.layoutManager =
            LinearLayoutManager(context)
        binding?.reOrderList?.adapter = adapterOrder

        adapterOrder.setOrders(order)


    }
}