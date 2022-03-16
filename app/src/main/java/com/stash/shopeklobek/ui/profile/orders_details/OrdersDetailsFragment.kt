package com.stash.shopeklobek.ui.profile.orders_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentOrdersDetailsBinding
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.entities.room.RoomOrder
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.home.product_details.ProductDetailsFragmentArgs
import com.stash.shopeklobek.utils.toCurrency

class OrdersDetailsFragment :BaseFragment<FragmentOrdersDetailsBinding>(FragmentOrdersDetailsBinding::inflate){
    private val args: OrdersDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var roomOrder: RoomOrder = args.roomOrder

        binding.tvCounter.text= getString(R.string.counter2)+" ${roomOrder.order.items?.size.toString()}"
       binding.tvAddress.text= getString(R.string.address2)+" ${roomOrder.order.billingAddress?.address}"
        binding.tvPhone.text= getString(R.string.phone2)+" ${roomOrder.order.billingAddress?.phone}"
        binding.tvDate.text=  getString(R.string.date)+" ${roomOrder.order.getDate()}"
        binding.tvDiscount.text= getString(R.string.discount2)+" ${roomOrder.order.totalDiscount}"
        binding.tvName.text=getString(R.string.name)+" ${roomOrder.order.billingAddress?.firstName}"
        binding.tvState.text= getString(R.string.state)+" ${roomOrder.order.financialStatus}"
        binding.totalPrice.text= getString(R.string.price)+ " ${activity?.let {
            roomOrder.order.finalPrice?.toCurrency(
                requireContext())
        }}"
        binding.tvOrderNumber.text=getString(R.string.order_number)+" ${roomOrder.order.orderNumber}"



    }

    }
