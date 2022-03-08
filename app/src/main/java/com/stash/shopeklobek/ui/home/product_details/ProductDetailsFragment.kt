package com.stash.shopeklobek.ui.home.product_details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.stash.shopeklobek.databinding.FragmentProductDetailsBinding
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.ui.BaseFragment

class ProductDetailsFragment :
    BaseFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate) {

    private val args: ProductDetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var myProduct: Products = args.product
        Log.d("onViewCreated2", "onViewCreated: " + myProduct)


        val imageSlider = binding.imageSlider
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(myProduct.images.get(0).src))
        imageList.add(SlideModel(myProduct.images.get(1).src))
        imageList.add(SlideModel(myProduct.images.get(2).src))
        //imageList.add(SlideModel(myProduct.images.get(3).src))
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        binding.tvNameItem.text=myProduct.title
        binding.tvPriceItem.text=myProduct.variants[0]?.price
        binding.tvItemDescription.text=myProduct.description
    }


}


