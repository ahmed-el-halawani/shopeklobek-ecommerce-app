package com.stash.shopeklobek.ui.home.brands

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.stash.shopeklobek.databinding.FragmentPriceBottomSheetBinding
import com.stash.shopeklobek.utils.toCurrency


class PriceBottomSheet : BottomSheetDialogFragment() {

    private val brandsViewModel: BrandsViewModel by activityViewModels()
    lateinit var binding : FragmentPriceBottomSheetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentPriceBottomSheetBinding.inflate(inflater,container,false)

        binding.firstPriceRadioButton.text = "0".toCurrency(requireContext())
        binding.firstPriceTextView.text = "100".toCurrency(requireContext())
        binding.secondPriceRadioButton.text = "100".toCurrency(requireContext())
        binding.secondPriceTextView.text = "200".toCurrency(requireContext())
        binding.thirdPriceRadioButton.text = "200".toCurrency(requireContext())

        return (binding.root)
    }

    override fun onResume() {
        super.onResume()

        binding.applyTextView2.setOnClickListener {
            if(binding.allPriceRadioButton.isChecked){
                brandsViewModel.firstPriceFilter.value=0f
                brandsViewModel.secondPriceFilter.value=1000f
            }
            if(binding.firstPriceRadioButton.isChecked){
                brandsViewModel.firstPriceFilter.value=0f
                brandsViewModel.secondPriceFilter.value=100f
            }
            if(binding.secondPriceRadioButton.isChecked){
                brandsViewModel.firstPriceFilter.value=100f
                brandsViewModel.secondPriceFilter.value=200f
            }
            if(binding.thirdPriceRadioButton.isChecked){
                brandsViewModel.firstPriceFilter.value=200f
                brandsViewModel.secondPriceFilter.value=1000f
            }
            dismiss()
        }
    }



}