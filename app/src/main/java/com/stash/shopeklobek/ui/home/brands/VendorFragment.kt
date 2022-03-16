package com.stash.shopeklobek.ui.home.brands


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentVendorBinding
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.home.categories.FilterBottomSheet
import com.stash.shopeklobek.utils.Constants
import com.stash.shopeklobek.utils.Constants.TAG
import com.stash.shopeklobek.utils.observeOnce


class VendorFragment : BaseFragment<FragmentVendorBinding>(FragmentVendorBinding::inflate) {

    private val args : VendorFragmentArgs by navArgs()
    private val brandsViewModel: BrandsViewModel by activityViewModels()
    private lateinit var adapter: VendorAdapter
    private lateinit var recyclerView: RecyclerView



override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {

        brandsViewModel.getProductsByVendor(args.vendor)
        recyclerView = binding.vendorRecyclerView

        binding.filterImageView.setOnClickListener {
            val priceBottomSheet = PriceBottomSheet()
            priceBottomSheet.show(parentFragmentManager,"TAG")
        }

        binding.searchTextView.addTextChangedListener {
            brandsViewModel.searching.value = binding.searchTextView.text.toString()
            brandsViewModel.searching.observe(viewLifecycleOwner, Observer { it2 ->
                brandsViewModel.vendors.observe(viewLifecycleOwner, Observer { it1 ->
                    when(it1) {
                        is Either.Success -> {
                                var list: MutableList<Products> = mutableListOf()
                                for (i in 0..it1.data.product.size.minus(1)) {
                                    var string = binding.searchTextView.text
                                    if (it1.data.product[i].title!!.contains(string, ignoreCase = true)) {
                                        list.add(it1.data.product[i])
                                    }
                                }
                                checkFavoriteList(list)
                            }
                        is Either.Error -> when (it1.errorCode) {
                            RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT).show()
                            RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            })
        }

        recyclerView.layoutManager = GridLayoutManager(requireContext(),2, RecyclerView.VERTICAL,false)

        /*brandsViewModel.vendors.observe(viewLifecycleOwner, Observer { it1 ->
            when(it1) {
                is Either.Success -> {
                    checkFavoriteList(it1.data.product)
                }
                is Either.Error -> when (it1.errorCode) {
                    RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT).show()
                    RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                }
            }
        })*/

        brandsViewModel.searching.observe(viewLifecycleOwner, Observer { it2 ->
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

        brandsViewModel.firstPriceFilter.observe(viewLifecycleOwner, Observer { first ->
            brandsViewModel.secondPriceFilter.observe(viewLifecycleOwner, Observer { second ->
                brandsViewModel.vendors.observe(viewLifecycleOwner, Observer { it1 ->
                    when(it1) {
                        is Either.Success -> {
                            checkFavoriteList(it1.data.product)
                        }
                        is Either.Error -> when (it1.errorCode) {
                            RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), resources.getString(R.string.errornoconnection), Toast.LENGTH_SHORT).show()
                            RepoErrors.ServerError -> Toast.makeText(requireContext(), resources.getString(R.string.errorloading), Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            })
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

    return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun checkFavoriteList(listOfProducts : List<Products> ){
        var listOfProductsWithFilter = ArrayList<Products>()
        val firstFilter = brandsViewModel.firstPriceFilter.value
        val secondFilter = brandsViewModel.secondPriceFilter.value
        for( i in 0.. listOfProducts.size.minus(1)){
            if(listOfProducts[i].variants[0]?.price?.toFloat() ?: 0f >= firstFilter!! && listOfProducts[i].variants[0]?.price?.toFloat() ?: 0f < secondFilter!! ){
                listOfProductsWithFilter.add(listOfProducts[i])
            }
        }
        when(val favorite = brandsViewModel.getFavorites()){
            is Either.Error -> {
                adapter = VendorAdapter(listOfProductsWithFilter,brandsViewModel::addToFavorite,brandsViewModel::deleteFavorite ,emptyList())
                recyclerView.adapter = adapter}
            is Either.Success -> {
                favorite.data.observeOnce(viewLifecycleOwner){
                    adapter = VendorAdapter(listOfProductsWithFilter,brandsViewModel::addToFavorite,brandsViewModel::deleteFavorite , it)
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        /*val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(Constants.FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constants.FIRST_FILTER_PRICE,"all")
        editor.apply()*/
    }

    override fun onDestroy() {
        super.onDestroy()
       /* brandsViewModel.firstPriceFilter.value=0f
        brandsViewModel.secondPriceFilter.value=1000f
        brandsViewModel.vendors.value=null*/
    }

}