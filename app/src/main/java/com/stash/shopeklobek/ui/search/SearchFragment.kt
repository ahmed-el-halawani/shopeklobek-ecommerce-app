package com.stash.shopeklobek.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stash.shopeklobek.databinding.FragmentSearchBinding
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.utils.observeOnce

class SearchFragment : com.stash.shopeklobek.ui.BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private lateinit var searchAdapter: SearchAdapter
    private val recyclerView: RecyclerView by lazy { binding.searchRecyclerView }
    private lateinit var searchViewModel: SearchViewModel
    var searching : MutableLiveData<String> = MutableLiveData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchFactory = SearchViewModel.Factory(requireActivity().application)
        searchViewModel = ViewModelProvider(this,searchFactory)[SearchViewModel::class.java]


        recyclerView.layoutManager = GridLayoutManager(requireContext(),2, RecyclerView.VERTICAL,false)

        searchViewModel.getFavorites()
        searchViewModel.products.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Either.Success -> {
                    checkFavoriteList(it.data.product)
                }
                is Either.Error -> when (it.errorCode) {
                    RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT).show()
                    RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.searchTextView2.addTextChangedListener {
            searching.value = binding.searchTextView2.text.toString()
        }

        searching.observe(viewLifecycleOwner, Observer { it2 ->
            searchViewModel.products.observe(viewLifecycleOwner, Observer { it1 ->
                when(it1) {
                    is Either.Success -> {
                        if(it2 == null){
                            checkFavoriteList(it1.data.product)
                        }else {
                            var list: MutableList<Products> = mutableListOf()
                            for (i in 0..it1.data.product.size.minus(1)) {
                                var string = binding.searchTextView2.text
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
        when(val favorite = searchViewModel.getFavorites()){
            is Either.Error -> {
                searchAdapter = SearchAdapter(listOfProducts,searchViewModel::addToFavorite,searchViewModel::deleteFavorite,emptyList())
                recyclerView.adapter = searchAdapter}
            is Either.Success -> {
                favorite.data.observeOnce(viewLifecycleOwner){
                    searchAdapter = SearchAdapter(listOfProducts,searchViewModel::addToFavorite, searchViewModel::deleteFavorite, it)
                    recyclerView.adapter = searchAdapter
                }
            }
        }
    }
}