package com.stash.shopeklobek.ui.home.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentCategoriesBinding
import com.stash.shopeklobek.databinding.FragmentHomeBinding
import com.stash.shopeklobek.ui.BaseFragment

class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>(FragmentCategoriesBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btProductDetails.setOnClickListener {
            findNavController().navigate(R.id.action_product_details)
            Log.i("TAG", "onViewCreated: catogru")
        }

        binding.filterLayout.setOnClickListener {
            val filterBottomSheet = FilterBottomSheet(binding.filterTextView,binding.filterTextView2)
            filterBottomSheet.show(parentFragmentManager,"TAG")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("TAG", "onCreateView: ")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}