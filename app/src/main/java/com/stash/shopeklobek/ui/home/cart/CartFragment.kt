package com.stash.shopeklobek.ui.home.cart

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentCartBinding
import com.stash.shopeklobek.databinding.FragmentHomeBinding
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.utils.ViewHelpers
import kotlinx.coroutines.launch

class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private val cartProductAdapter by lazy {
        CartProductsAdapter().apply {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()

        binding.apply {

            btnProceedToCheckout.setOnClickListener {
                println("herere")
                findNavController().navigate(R.id.action_cartFragment_to_shappingAddressesFragment)
            }

            ItemTouchHelper(ViewHelpers.SwipeToRemove { position ->
//                val weatherLang = favoritesAdapter.differ.currentList[position]
//
//                //delete article
//                favoritesViewModel.deleteWeatherLang(weatherLang)

                // set undo option
//                showSnackbar2(getString(R.string.remove_location_done)) {
//                    favoritesViewModel.addWeatherLang(weatherLang)
//                }
                Toast.makeText(requireContext(), "item deleted$position", Toast.LENGTH_SHORT).show()
            }).attachToRecyclerView(rvCartProducts)

        }
    }
    private fun setupRecycleView() {
        binding.rvCartProducts.apply {
            adapter = cartProductAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }



}