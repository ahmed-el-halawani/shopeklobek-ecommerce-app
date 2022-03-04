package com.stash.shopeklobek.ui.home.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentFilterBottomSheetBinding
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors


class FilterBottomSheet(var firstFilter : TextView , var secondFilter : TextView,
                        var hashMap: HashMap<String,Long>, var recyclerView: RecyclerView) : BottomSheetDialogFragment(){

    lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    lateinit var binding : FragmentFilterBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val categoryViewModelFactory = CategoriesViewModel.Factory(requireActivity().application)
        categoriesViewModel = ViewModelProvider(this, categoryViewModelFactory)[CategoriesViewModel::class.java]
        binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)

        binding.applyTextView.setOnClickListener {
            if(binding.womenRadioButton.isChecked) {
                firstFilter.text = resources.getString(R.string.women)
                setFilter("women")
            }
            if(binding.menRadioButton.isChecked){
                firstFilter.text=resources.getString(R.string.men)
                setFilter("men")
            }
            if(binding.kidsRadioButton.isChecked) {
                firstFilter.text = resources.getString(R.string.kids)
                setFilter("kid")
            }
            if(binding.saleRadioButton.isChecked) {
                firstFilter.text = resources.getString(R.string.sale)
                setFilter("sale")
            }

            categoriesViewModel.products.observe(viewLifecycleOwner, Observer {
                when(it) {
                    is Either.Success -> {
                        categoryAdapter = CategoryAdapter(it.data.product,requireContext(),this.requireParentFragment())
                        recyclerView.layoutManager = GridLayoutManager(requireContext(),2, RecyclerView.VERTICAL,false)
                        recyclerView.adapter = categoryAdapter
                    }
                    is Either.Error -> when (it.errorCode) {
                        RepoErrors.NoInternetConnection -> Toast.makeText(requireContext(), "No Connection", Toast.LENGTH_SHORT).show()
                        RepoErrors.ServerError -> Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        return (binding.root)
    }

    private fun setFilter(firstFilter: String){
        if(binding.allRadioButton.isChecked) {
            secondFilter.text = resources.getString(R.string.all)
            categoriesViewModel.getProductsByGender(hashMap.getValue(firstFilter))
        }
        if(binding.shoesRadioButton.isChecked) {
            secondFilter.text = resources.getString(R.string.shoes)
            categoriesViewModel.getProductsFromType(hashMap.getValue(firstFilter),"SHOES")
        }
        if(binding.accessoriesRadioButton.isChecked) {
            secondFilter.text = resources.getString(R.string.accessories)
            categoriesViewModel.getProductsFromType(hashMap.getValue(firstFilter),"ACCESSORIES")
        }
        if(binding.clothesRadioButton.isChecked) {
            secondFilter.text = resources.getString(R.string.clothes)
            categoriesViewModel.getProductsFromType(hashMap.getValue(firstFilter),"T-SHIRTS")
        }
    }
}