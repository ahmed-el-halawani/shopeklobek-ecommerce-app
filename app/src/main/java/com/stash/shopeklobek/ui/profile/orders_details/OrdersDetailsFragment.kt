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

        val imageSlider = binding.imageSlider
        val imageList = ArrayList<SlideModel>()

        for (i in  roomOrder.order.items?.get(0)?.product!!.images) {
            imageList.add(SlideModel(i.src))
        }
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        binding.tvCounter.text= getString(R.string.counter)+roomOrder.order.items?.get(0)?.count.toString()
        binding.tvAddress.text= getString(R.string.address)+roomOrder.order.billingAddress?.address
        binding.tvDate.text=  getString(R.string.date)+roomOrder.order.getDate()
        binding.tvDiscount.text= getString(R.string.discount)+roomOrder.order.totalDiscount
        binding.tvNameItem.text=roomOrder.order.id.toString()
        binding.tvState.text= getString(R.string.state)+roomOrder.order.state
        binding.totalPrice.text= getString(R.string.price)+ activity?.let {
            roomOrder.order.price?.toCurrency(
                it.applicationContext)
        }


    }

    }
