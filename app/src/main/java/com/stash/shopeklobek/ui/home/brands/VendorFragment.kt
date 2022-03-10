package com.stash.shopeklobek.ui.home.brands


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
import com.stash.shopeklobek.utils.Constants.TAG
import com.stash.shopeklobek.utils.observeOnce


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

        brandsViewModel.getProductsByVendor(args.vendor)
        recyclerView = binding.vendorRecyclerView

        binding.searchTextView.addTextChangedListener {
            searching.value = binding.searchTextView.text.toString()
        }
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2, RecyclerView.VERTICAL,false)

        brandsViewModel.vendors.observe(viewLifecycleOwner, Observer { it1 ->
            when(it1) {
                is Either.Success -> {
                    checkFavoriteList(it1.data.product)
                }
                is Either.Error -> when (it1.errorCode) {
                    RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT).show()
                    RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        searching.observe(viewLifecycleOwner, Observer { it2 ->
            brandsViewModel.vendors.observe(viewLifecycleOwner, Observer { it1 ->
                when(it1) {
                    is Either.Success -> {
                        if(it2 == null){
                            checkFavoriteList(it1.data.product)
                        }else {
                            var list: MutableList<Products> = mutableListOf()
                            for (i in 0..it1.data.product.size.minus(1)) {
                                var string = binding.searchTextView.text
                                if (it1.data.product[i].title!!.contains(string, ignoreCase = true)) {
                                    list.add(it1.data.product[i])
                                }
                            }
                            checkFavoriteList(list)
                        }
                    }
                    is Either.Error -> when (it1.errorCode) {
                        RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT).show()
                        RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        })

    }

    fun checkFavoriteList(listOfProducts : List<Products> ){
        when(val favorite = brandsViewModel.getFavorites()){
            is Either.Error -> {
                adapter = VendorAdapter(listOfProducts,brandsViewModel::addToFavorite,emptyList())
                recyclerView.adapter = adapter}
            is Either.Success -> {
                favorite.data.observeOnce(viewLifecycleOwner){
                    adapter = VendorAdapter(listOfProducts,brandsViewModel::addToFavorite, it)
                    recyclerView.adapter = adapter
                }
            }
        }
    }

}