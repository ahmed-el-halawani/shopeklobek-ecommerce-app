package com.stash.shopeklobek.ui.home.brands

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentBrandsBinding
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.utils.Constants.TAG

class BrandsFragment : BaseFragment<FragmentBrandsBinding>(FragmentBrandsBinding::inflate) {

    private lateinit var brandsAdapter: BrandsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var brandsViewModel: BrandsViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val brandsViewModelFactory = BrandsViewModel.Factory(requireActivity().application)
        brandsViewModel = ViewModelProvider(this, brandsViewModelFactory)[BrandsViewModel::class.java]

        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel("https://cdn5.vectorstock.com/i/1000x1000/27/29/sale-banner-black-background-shop-now-vector-21582729.jpg"))
        imageList.add(SlideModel("https://media.istockphoto.com/vectors/super-sale-banner-or-poster-design-with-80-discount-offer-and-on-vector-id1176999923?k=20&m=1176999923&s=612x612&w=0&h=Xnb177iy2-jdCQlvx9cQ1jZzEOe1lGrANTlfsl7CIvI="))
        imageList.add(SlideModel("https://static.vecteezy.com/system/resources/previews/000/590/172/non_2x/dynamic-final-sale-banner-up-to-50-off-vector-illustration-modern-flash-sale-banners-sale-banner-template-design.jpg"))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                Toast.makeText(requireContext(), "Clicked on Slider", Toast.LENGTH_SHORT).show()
            }
        })
        recyclerView = binding.brandRecyclerView

        brandsViewModel.getSmartCollection()
        brandsViewModel.brands.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Either.Success -> {

                    brandsAdapter = BrandsAdapter(it.data.smart_collections!!)
                    recyclerView.layoutManager = GridLayoutManager(requireContext(),2,RecyclerView.VERTICAL,false)
                    recyclerView.adapter = brandsAdapter
                }
                is Either.Error -> when (it.errorCode) {
                    RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT)
                        .show()
                    RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                    RepoErrors.EmptyBody -> Toast.makeText(requireContext(), "empty body!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        brandsViewModel.discounts.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Either.Success -> {

                    Log.i(TAG, "onViewCreated: "+ (it.data.discount?.get(0)?.value ?: 0))
                }
            }
        })


        /*brandsViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                true ->{
                    showLoading()
                }
                false -> {
                    hideLoading()
                }
            }
        })
    }

        })*/
    }
}