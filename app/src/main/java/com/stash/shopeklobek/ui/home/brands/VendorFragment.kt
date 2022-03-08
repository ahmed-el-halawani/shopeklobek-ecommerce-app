package com.stash.shopeklobek.ui.home.brands


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stash.shopeklobek.databinding.FragmentVendorBinding
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.ui.BaseFragment


class VendorFragment : BaseFragment<FragmentVendorBinding>(FragmentVendorBinding::inflate) {

    private val args : VendorFragmentArgs by navArgs()
    private lateinit var adapter: VendorAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var brandsViewModel: BrandsViewModel
    var searching : MutableLiveData<String> = MutableLiveData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val brandsViewModelFactory = BrandsViewModel.Factory(requireActivity().application)
        brandsViewModel = ViewModelProvider(this, brandsViewModelFactory)[BrandsViewModel::class.java]

        brandsViewModel.getFavorites()
        brandsViewModel.getProductsByVendor(args.vendor)
        recyclerView = binding.vendorRecyclerView

        binding.searchImageView.setOnClickListener {
            searching.value = binding.searchTextView.text.toString()
        }

        brandsViewModel.vendors.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Either.Success -> {
                    adapter = VendorAdapter(it.data.product,brandsViewModel::addToFavorite, brandsViewModel.favorites.value?: emptyList())
                    recyclerView.layoutManager = GridLayoutManager(requireContext(),2, RecyclerView.VERTICAL,false)
                    recyclerView.adapter = adapter
                }
                is Either.Error -> when (it.errorCode) {
                    RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT).show()
                    RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        searching.observe(viewLifecycleOwner, Observer { it1 ->
            brandsViewModel.vendors.observe(viewLifecycleOwner, Observer {
                when(it) {
                    is Either.Success -> {
                        if(it1 == null){
                            adapter = VendorAdapter(it.data.product,brandsViewModel::addToFavorite, brandsViewModel.favorites.value?: emptyList())
                        }else {
                            var list: MutableList<Products> = mutableListOf()
                            for (i in 0..it.data.product.size.minus(1)) {
                                var string = binding.searchTextView.text
                                if (it.data.product[i].title!!.contains(string, ignoreCase = true)) {
                                    list.add(it.data.product[i])
                                }
                            }
                            adapter = VendorAdapter(list,brandsViewModel::addToFavorite, brandsViewModel.favorites.value?: emptyList())
                        }
                        recyclerView.layoutManager = GridLayoutManager(requireContext(),2, RecyclerView.VERTICAL,false)
                        recyclerView.adapter = adapter
                    }
                    is Either.Error -> when (it.errorCode) {
                        RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT).show()
                        RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        })

    }

}