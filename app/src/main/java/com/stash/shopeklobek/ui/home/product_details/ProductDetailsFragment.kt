package com.stash.shopeklobek.ui.home.product_details

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentProductDetailsBinding
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.home.favorites.FavoritesViewModel
import com.stash.shopeklobek.utils.Constants.TAG
import com.stash.shopeklobek.utils.toCurrency
import kotlinx.coroutines.launch

class ProductDetailsFragment :
    BaseFragment<FragmentProductDetailsBinding>(FragmentProductDetailsBinding::inflate) {
    private val productDetailsViewModel: ProductDetailsViewModel by activityViewModels()

    private val args: ProductDetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var myProduct: Products = args.product

        val imageSlider = binding.imageSlider
        val imageList = ArrayList<SlideModel>()

        for (i in myProduct.images) {
            imageList.add(SlideModel(i.src))
        }

        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        binding.tvNameItem.text = myProduct.title
        binding.tvPriceItem.text =
            activity?.let { myProduct.variants[0]?.price?.toCurrency(it.applicationContext) }
        binding.tvItemDescription.text = myProduct.description

        // set in room
        // add to cart
        binding.btnAddToCart.setOnClickListener {

            productDetailsViewModel.repo.getSettingsLiveData().observe(viewLifecycleOwner) {
                if (it.customer == null) {
                    Toast.makeText(activity, getString(R.string.please_login), Toast.LENGTH_LONG)
                        .show()

                } else {
                    lifecycleScope.launch {
                        productDetailsViewModel.addToCart(myProduct)
                        Toast.makeText(
                            activity,
                            getString(R.string.added_to_cart),
                            Toast.LENGTH_LONG
                        ).show()


                    }
                }
            }

        }
        // set in room
        //add to favorite
        binding.btAddFavorite.setOnClickListener {

            productDetailsViewModel.repo.getSettingsLiveData().observe(viewLifecycleOwner) {
                if (it.customer == null) {
                    Toast.makeText(activity, getString(R.string.please_login), Toast.LENGTH_LONG)
                        .show()

                } else {
                    lifecycleScope.launch {
                        productDetailsViewModel.addToFavorite(myProduct)
                        Toast.makeText(
                            activity,
                            getString(R.string.added_to_favorite),
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
            }
        }


    }


}


