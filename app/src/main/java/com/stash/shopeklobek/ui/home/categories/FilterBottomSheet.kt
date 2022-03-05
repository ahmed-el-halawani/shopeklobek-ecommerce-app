package com.stash.shopeklobek.ui.home.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stash.shopeklobek.databinding.FragmentFilterBottomSheetBinding


class FilterBottomSheet(var hashMap: HashMap<String,Long>) : BottomSheetDialogFragment(){

    private val categoriesViewModel: CategoriesViewModel by activityViewModels()
    lateinit var binding : FragmentFilterBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        /*val categoryViewModelFactory = CategoriesViewModel.Factory(requireActivity().application)
        categoriesViewModel = ViewModelProvider(this, categoryViewModelFactory)[CategoriesViewModel::class.java]*/
        binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)

        binding.applyTextView.setOnClickListener {
            if(binding.womenRadioButton.isChecked) {
                setFilter("women")
            }
            if(binding.menRadioButton.isChecked){
                setFilter("men")
            }
            if(binding.kidsRadioButton.isChecked) {
                setFilter("kid")
            }
            if(binding.saleRadioButton.isChecked) {
                setFilter("sale")
            }
            dismiss()
        }
        return (binding.root)
    }

    private fun setFilter(firstFilter: String){
        if(binding.allRadioButton.isChecked) {
            categoriesViewModel.getProductsByGender(hashMap.getValue(firstFilter),firstFilter)
        }
        if(binding.shoesRadioButton.isChecked) {
            categoriesViewModel.getProductsFromType(hashMap.getValue(firstFilter),"SHOES",firstFilter)
        }
        if(binding.accessoriesRadioButton.isChecked) {
            categoriesViewModel.getProductsFromType(hashMap.getValue(firstFilter),"ACCESSORIES",firstFilter)
        }
        if(binding.clothesRadioButton.isChecked) {
            categoriesViewModel.getProductsFromType(hashMap.getValue(firstFilter),"T-SHIRTS",firstFilter)
        }
    }
}