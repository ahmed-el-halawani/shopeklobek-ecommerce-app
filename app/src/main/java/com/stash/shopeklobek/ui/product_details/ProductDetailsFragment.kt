package com.stash.shopeklobek.ui.product_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentProductDetailsBinding
import com.stash.shopeklobek.databinding.FragmentProfileBinding
import com.stash.shopeklobek.ui.BaseFragment

class ProductDetailsFragment: BaseFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageSlider =binding.imageSlider
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://contents.mediadecathlon.com/p2174723/k\$9ea1c903bf5150201eeb0d28a25e4d34/kalenji-run-support-women-s-running-shoes-dark-grey.jpg?&f=800x800"))
        imageList.add(SlideModel("https://contents.mediadecathlon.com/p2028402/k\$4e2469b2a84c92260fede7d9a4672489/kalenji-run-support-women-s-running-shoes-dark-grey.jpg?&f=800x800"))
        imageList.add(SlideModel("https://contents.mediadecathlon.com/p2028400/k\$203c3a829731ebcb3c812101cb502bfc/kalenji-run-support-women-s-running-shoes-dark-grey.jpg?&f=800x800" ))
        imageList.add(SlideModel("https://contents.mediadecathlon.com/p2028409/k\$521864b2fb7a7c73c4bf0cdfbb3a3a0f/kalenji-run-support-women-s-running-shoes-dark-grey.jpg?&f=800x800" ))

        imageSlider.setImageList(imageList,ScaleTypes.FIT)
    }




}


