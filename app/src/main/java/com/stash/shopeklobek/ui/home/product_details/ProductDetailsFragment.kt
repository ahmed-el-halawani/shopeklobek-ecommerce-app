package com.stash.shopeklobek.ui.home.product_details

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.stash.shopeklobek.databinding.FragmentProductDetailsBinding
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.utils.Constants.TAG
import kotlinx.coroutines.launch

class ProductDetailsFragment :
    BaseFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate) {

    private val args: ProductDetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repo = ProductRepo(ShopifyApi.api, SettingsPreferences.getInstance(context?.applicationContext as Application),context?.applicationContext as Application)


        var myProduct: Products = args.product
        Log.d("onViewCreated2", "onViewCreated: " + myProduct)


        val imageSlider = binding.imageSlider
        val imageList = ArrayList<SlideModel>()


        for (i in myProduct.images){
            imageList.add(SlideModel(i.src))
        }

        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        binding.tvNameItem.text=myProduct.title
        binding.tvPriceItem.text=myProduct.variants[0]?.price
        binding.tvItemDescription.text=myProduct.description

        binding.btnAddToCart.setOnClickListener {
            lifecycleScope.launch {
                repo.addToCart(myProduct)
            }
        }
    }


}


