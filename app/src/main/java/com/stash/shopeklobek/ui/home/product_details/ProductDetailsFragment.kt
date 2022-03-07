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
import com.stash.shopeklobek.utils.Constants.TAG

class ProductDetailsFragment: BaseFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate){

    private val args : ProductDetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var myProduct : Products = args.product
        Log.i(TAG, "onViewCreated: "+myProduct)





        val imageSlider =binding.imageSlider
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://contents.mediadecathlon.com/p2174723/k\$9ea1c903bf5150201eeb0d28a25e4d34/kalenji-run-support-women-s-running-shoes-dark-grey.jpg?&f=800x800"))
        imageList.add(SlideModel("https://contents.mediadecathlon.com/p2028402/k\$4e2469b2a84c92260fede7d9a4672489/kalenji-run-support-women-s-running-shoes-dark-grey.jpg?&f=800x800"))
        imageList.add(SlideModel("https://contents.mediadecathlon.com/p2028400/k\$203c3a829731ebcb3c812101cb502bfc/kalenji-run-support-women-s-running-shoes-dark-grey.jpg?&f=800x800" ))
        imageList.add(SlideModel("https://contents.mediadecathlon.com/p2028409/k\$521864b2fb7a7c73c4bf0cdfbb3a3a0f/kalenji-run-support-women-s-running-shoes-dark-grey.jpg?&f=800x800" ))

        imageSlider.setImageList(imageList,ScaleTypes.FIT)
    }



}


