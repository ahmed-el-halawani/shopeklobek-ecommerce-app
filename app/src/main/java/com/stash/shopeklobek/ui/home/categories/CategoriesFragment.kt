package com.stash.shopeklobek.ui.home.categories

import android.media.metrics.LogSessionId
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentCategoriesBinding
import com.stash.shopeklobek.databinding.FragmentHomeBinding
import com.stash.shopeklobek.model.api.Either
import com.stash.shopeklobek.model.api.RepoErrors
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.home.brands.BrandsAdapter
import com.stash.shopeklobek.ui.home.brands.BrandsViewModel
import com.stash.shopeklobek.utils.Constants.TAG

class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>(FragmentCategoriesBinding::inflate) {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var categoriesViewModel: CategoriesViewModel
    private val hashMap:HashMap<String,Long> = HashMap()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryViewModelFactory = CategoriesViewModel.Factory(requireActivity().application)
        categoriesViewModel = ViewModelProvider(this, categoryViewModelFactory)[CategoriesViewModel::class.java]

        binding.btProductDetails.setOnClickListener {
            findNavController().navigate(R.id.action_product_details)
        }

        binding.filterLayout.setOnClickListener {
            val filterBottomSheet = FilterBottomSheet(binding.filterTextView,binding.filterTextView2, hashMap,recyclerView)
            filterBottomSheet.show(parentFragmentManager,"TAG")
        }

        //categoriesViewModel.getAllCategory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        recyclerView = binding.categoryRecyclerView
        val categoryViewModelFactory = CategoriesViewModel.Factory(requireActivity().application)
        categoriesViewModel = ViewModelProvider(this, categoryViewModelFactory)[CategoriesViewModel::class.java]
        categoriesViewModel.getMainCategory()
        categoriesViewModel.category.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Either.Success -> {
                    for (i in 0 ..((it.data.collections?.size)?.minus(1) ?: 0)){
                        hashMap[it.data.collections?.get(i)?.collectionsHandle!!] = it.data.collections[i].collectionsId!!
                    }
                    Log.i(TAG, "onCreateView: "+hashMap)
                }
                is Either.Error -> when (it.errorCode) {
                    RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT).show()
                    RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                }
            }
        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}