package com.stash.shopeklobek.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.stash.shopeklobek.databinding.FragmentOrdersBinding
import com.stash.shopeklobek.model.entities.Order
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.utils.Constants

class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate)
{

    private lateinit var adapterOrder: AdapterOrder
    private lateinit var order: ArrayList<Order>
    private val profileViewModel by lazy{
        ProfileViewModel.create(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bandle = Bundle().apply {
            putString("id","")
        }
        activity?.let {
            val sharedPreferences: SharedPreferences = it.getSharedPreferences(
                "sharedPrefFile",
                Context.MODE_PRIVATE
            )

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt(Constants.ONCLICK_PROFILE, 2)
            editor.apply()
            editor.commit()

        }

       // findNavController().navigate(2,bandle)


        adapterOrder = AdapterOrder(ArrayList())

        binding.reOrderList.layoutManager =
            LinearLayoutManager(context)
        binding?.reOrderList?.adapter = adapterOrder


        when(val res=profileViewModel.getOrders())
        {
            is Either.Error -> {
                Log.d("onViewCreated", "nullll : error code" +res.errorCode+" : message :"+res
                    .message)
            }
            is Either.Success -> {
                res.data.observe(viewLifecycleOwner){
                    if (!it.isEmpty()) {
                        adapterOrder.setOrders(it.reversed())
                        binding.ivEmptyOrders.visibility = View.GONE
                    } else {
                        binding.ivEmptyOrders.visibility = View.VISIBLE
                    }
                }

            }}




    }
}