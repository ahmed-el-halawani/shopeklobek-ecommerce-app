package com.stash.shopeklobek.ui.home.brands

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.stash.shopeklobek.databinding.FragmentBrandsBinding
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.ui.BaseFragment

class BrandsFragment : BaseFragment<FragmentBrandsBinding>(FragmentBrandsBinding::inflate) {

    private lateinit var brandsAdapter: BrandsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var brandsViewModel: BrandsViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val brandsViewModelFactory = BrandsViewModel.Factory(requireActivity().application)
        brandsViewModel = ViewModelProvider(this, brandsViewModelFactory)[BrandsViewModel::class.java]
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://bit.ly/2YoJ77H", "The animal population decreased by 58 percent in 42 years."))
        imageList.add(SlideModel("https://bit.ly/2BteuF2", "Elephants and tigers may become extinct."))
        imageList.add(SlideModel("https://bit.ly/3fLJf72", "And people do that."))

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
                    brandsAdapter = BrandsAdapter(it.data.smart_collections!!, requireContext(), this.requireParentFragment())
                    recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
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

        brandsViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    showLoading()
                }
                false -> {
                    hideLoading()
                }
            }
        })
    }

}