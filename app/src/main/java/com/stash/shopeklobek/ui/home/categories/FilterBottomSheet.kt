package com.stash.shopeklobek.ui.home.categories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentFilterBottomSheetBinding

class FilterBottomSheet(var firstFilter : TextView , var secondFilter : TextView) : BottomSheetDialogFragment(){

    lateinit var binding : FragmentFilterBottomSheetBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)

        binding.applyTextView.setOnClickListener {
            if(binding.womenRadioButton.isChecked) {
                firstFilter.text = resources.getString(R.string.women)
            }
            if(binding.menRadioButton.isChecked){
                firstFilter.text=resources.getString(R.string.men)
            }
            if(binding.kidsRadioButton.isChecked) {
                firstFilter.text = resources.getString(R.string.kids)
            }
            if(binding.saleRadioButton.isChecked) {
                firstFilter.text = resources.getString(R.string.sale)
            }
            if(binding.shoesRadioButton.isChecked) {
                secondFilter.text = resources.getString(R.string.shoes)
            }
            if(binding.accessoriesRadioButton.isChecked) {
                secondFilter.text = resources.getString(R.string.accessories)
            }
            if(binding.clothesRadioButton.isChecked) {
                secondFilter.text = resources.getString(R.string.clothes)
            }

            dismiss()
        }
        return (binding.root)
    }


}