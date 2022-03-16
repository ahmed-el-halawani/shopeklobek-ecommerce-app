package com.stash.shopeklobek.ui.home.categories

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentFilterBottomSheetBinding
import com.stash.shopeklobek.utils.Constants
import com.stash.shopeklobek.utils.Constants.FILE_NAME
import com.stash.shopeklobek.utils.Constants.FIRST_FILTER_CATEGORIES
import com.stash.shopeklobek.utils.Constants.SECOND_FILTER_CATEGORIES
import com.stash.shopeklobek.utils.toCurrency


class FilterBottomSheet() : BottomSheetDialogFragment(){

    private val categoriesViewModel: CategoriesViewModel by activityViewModels()
    lateinit var binding : FragmentFilterBottomSheetBinding
    private lateinit var hashMap : HashMap<String,Long>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)

        hashMap = categoriesViewModel.hashmap

        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        when(sharedPreferences.getString(FIRST_FILTER_CATEGORIES,"women")){
           "women" -> binding.womenRadioButton.isChecked = true
            "men" -> binding.menRadioButton.isChecked = true
            "kid" -> binding.kidsRadioButton.isChecked = true
            "sale" -> binding.saleRadioButton.isChecked = true
        }
        when(sharedPreferences.getString(SECOND_FILTER_CATEGORIES,"all")){
            "all" -> binding.allRadioButton.isChecked = true
            "SHOES" -> binding.shoesRadioButton.isChecked = true
            "ACCESSORIES" -> binding.accessoriesRadioButton.isChecked = true
            "sale" -> binding.saleRadioButton.isChecked = true
        }
        when(sharedPreferences.getString(Constants.CATEGORY_FILTER_PRICE,"all")){
            "all" -> binding.allPriceCategoryRadioButton.isChecked = true
            "first" -> binding.firstPriceCategoryRadioButton.isChecked = true
            "second" -> binding.secondPriceCategoryRadioButton.isChecked = true
            "third" -> binding.thirdPriceCategoryRadioButton.isChecked = true
        }

        binding.firstPriceCategoryRadioButton.text = "0".toCurrency(requireContext())
        binding.firstPriceCategoryTextView.text = "100".toCurrency(requireContext())
        binding.secondPriceCategoryRadioButton.text = "100".toCurrency(requireContext())
        binding.secondPriceCategoryTextView.text = "200".toCurrency(requireContext())
        binding.thirdPriceCategoryRadioButton.text = "200".toCurrency(requireContext())


        binding.applyTextView.setOnClickListener {
            val editor = sharedPreferences.edit()
            if(binding.womenRadioButton.isChecked) {
                setFilter("women")
                editor.putString(FIRST_FILTER_CATEGORIES,"women")
            }
            if(binding.menRadioButton.isChecked){
                setFilter("men")
                editor.putString(FIRST_FILTER_CATEGORIES,"men")
            }
            if(binding.kidsRadioButton.isChecked) {
                setFilter("kid")
                editor.putString(FIRST_FILTER_CATEGORIES,"kid")
            }
            if(binding.saleRadioButton.isChecked) {
                setFilter("sale")
                editor.putString(FIRST_FILTER_CATEGORIES,"sale")
            }
            if(binding.allPriceCategoryRadioButton.isChecked){
                categoriesViewModel.firstPriceFilter.value=0f
                categoriesViewModel.secondPriceFilter.value=1000f
                editor.putString(Constants.CATEGORY_FILTER_PRICE,"all")

            }
            if(binding.firstPriceCategoryRadioButton.isChecked){
                categoriesViewModel.firstPriceFilter.value=0f
                categoriesViewModel.secondPriceFilter.value=100f
                editor.putString(Constants.CATEGORY_FILTER_PRICE,"first")
            }
            if(binding.secondPriceCategoryRadioButton.isChecked){
                categoriesViewModel.firstPriceFilter.value=100f
                categoriesViewModel.secondPriceFilter.value=200f
                editor.putString(Constants.CATEGORY_FILTER_PRICE,"second")
            }
            if(binding.thirdPriceCategoryRadioButton.isChecked){
                categoriesViewModel.firstPriceFilter.value=200f
                categoriesViewModel.secondPriceFilter.value=1000f
                editor.putString(Constants.CATEGORY_FILTER_PRICE,"third")
            }
            editor.apply()
            dismiss()
        }
        return (binding.root)
    }

    private fun setFilter(firstFilter: String) {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (!hashMap.isEmpty()) {
            if (binding.allRadioButton.isChecked) {
                categoriesViewModel.getProductsByGender(hashMap.getValue(firstFilter), firstFilter)
                editor.putString(SECOND_FILTER_CATEGORIES, "all")
            }
            if (binding.shoesRadioButton.isChecked) {
                categoriesViewModel.getProductsFromType(
                    hashMap.getValue(firstFilter),
                    "SHOES",
                    firstFilter
                )
                editor.putString(SECOND_FILTER_CATEGORIES, "SHOES")
            }
            if (binding.accessoriesRadioButton.isChecked) {
                categoriesViewModel.getProductsFromType(
                    hashMap.getValue(firstFilter),
                    "ACCESSORIES",
                    firstFilter
                )
                editor.putString(SECOND_FILTER_CATEGORIES, "ACCESSORIES")
            }
            if (binding.clothesRadioButton.isChecked) {
                categoriesViewModel.getProductsFromType(
                    hashMap.getValue(firstFilter),
                    "T-SHIRTS",
                    firstFilter
                )
                editor.putString(SECOND_FILTER_CATEGORIES, "T-SHIRTS")
            }
            editor.apply()
        }else{
            Toast.makeText(requireContext(), resources.getString(R.string.errorloading), Toast.LENGTH_SHORT).show()
        }
    }
}