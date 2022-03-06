package com.stash.shopeklobek.ui.home.brands


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.databinding.FragmentVendorBinding
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.ui.home.categories.CategoryAdapter
import com.stash.shopeklobek.utils.Constants


class VendorFragment : BaseFragment<FragmentVendorBinding>(FragmentVendorBinding::inflate) {

    private val args : VendorFragmentArgs by navArgs()
    private lateinit var adapter: CategoryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var brandsViewModel: BrandsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val brandsViewModelFactory = BrandsViewModel.Factory(requireActivity().application)
        brandsViewModel = ViewModelProvider(this, brandsViewModelFactory)[BrandsViewModel::class.java]

        brandsViewModel.getProductsByVendor(args.vendor)
        recyclerView = binding.vendorRecyclerView
        brandsViewModel.vendors.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Either.Success -> {
                    Log.i(Constants.TAG, "onResume: ")
                    adapter = CategoryAdapter(it.data.product,requireContext(),this.requireParentFragment())
                    recyclerView.layoutManager = GridLayoutManager(requireContext(),2, RecyclerView.VERTICAL,false)
                    recyclerView.adapter = adapter
                }
                is Either.Error -> when (it.errorCode) {
                    RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT).show()
                    RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}