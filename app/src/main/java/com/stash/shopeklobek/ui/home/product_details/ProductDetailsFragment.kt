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

        val imageSlider = binding.imageSlider
        val imageList = ArrayList<SlideModel>()

        for(i in 0 .. myProduct.images.size.minus(1)){
            imageList.add(SlideModel(myProduct.images[i].src))
        }
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        binding.tvNameItem.text=myProduct.title
        binding.tvPriceItem.text=myProduct.variants[0]?.price
        binding.tvItemDescription.text=myProduct.description
    }


}


