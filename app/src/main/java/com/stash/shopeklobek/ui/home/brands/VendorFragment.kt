package com.stash.shopeklobek.ui.home.brands


import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.databinding.FragmentVendorBinding
import com.stash.shopeklobek.ui.home.categories.CategoryAdapter


class VendorFragment : BaseFragment<FragmentVendorBinding>(FragmentVendorBinding::inflate) {

    private lateinit var adapter: CategoryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var brandsViewModel: BrandsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteViewModelFactory = BrandsViewModel.Factory(requireActivity().application)
        brandsViewModel = ViewModelProvider(this, favoriteViewModelFactory)[BrandsViewModel::class.java]
    }

}