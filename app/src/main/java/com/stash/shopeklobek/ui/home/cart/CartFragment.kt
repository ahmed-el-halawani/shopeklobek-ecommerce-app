package com.stash.shopeklobek.ui.home.cart

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentCartBinding
import com.stash.shopeklobek.model.entities.room.RoomCart
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.checkout.CheckoutActivity
import com.stash.shopeklobek.utils.CurrencyUtil
import com.stash.shopeklobek.utils.ViewHelpers
import kotlinx.coroutines.launch

class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {


    private val cartProductAdapter by lazy {
        CartProductsAdapter().apply {
            setOnDecrementClickListener {
                println("setOnDecrementClickListener")
                if (it.count > 1) {
                    println("setOnDecrementClickListener")
                    lifecycleScope.launch {
                        when (cartViewModel.updateCartProduct(
                            it.copy(
                                count = it.count - 1
                            )
                        )) {
                            is Either.Error -> {
                                Toast.makeText(context, "someThing wrong happened , please try again", Toast.LENGTH_SHORT).show()
                            }
                            is Either.Success -> Unit
                        }
                    }
                } else {
                    deleteProductDialog(it)
                }
            }

            setOnIncrementClickListener {
                println("setOnIncrementClickListener")

                if ((it.variant()?.quantity) != null && it.count < it.variant()?.quantity!!) {
                    println("setOnIncrementClickListener")

                    lifecycleScope.launch {
                        when (cartViewModel.updateCartProduct(
                            it.copy(
                                count = it.count + 1
                            )
                        )) {
                            is Either.Error -> {
                                Toast.makeText(context, "someThing wrong happened , please try again", Toast.LENGTH_SHORT).show()
                            }
                            is Either.Success -> Unit
                        }
                    }
                } else {
                    messageDialog()
                }
            }
        }
    }

    private val cartViewModel by lazy {
        CartViewModel.create(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setupRecycleView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            if (cartViewModel.productRepo.getSettings().customer == null) {
                println("herere")
                cvCartDetails.visibility = View.GONE
                findNavController().navigate(R.id.action_cartFragment_to_loginFragment2)
            } else {
                cartViewModel.getCartProducts()

                cartViewModel.cartLiveData.observe(viewLifecycleOwner) { roomCarts ->
                    cvCartDetails.visibility = if (roomCarts.isEmpty()) View.GONE else View.VISIBLE

                    cartProductAdapter.differ.submitList(roomCarts)

                    var price = 0.0
                    var count = 0

                    roomCarts.forEach {
                        price += (it.variant()?.price?.toDouble() ?: 0.0) * it.count
                        count += it.count
                    }

                    tvTotalProductsPrice.text = CurrencyUtil.convertCurrency(price.toString())
                    tvTotalItems.text = count.toString()

                }

                btnProceedToCheckout.setOnClickListener {
                    startActivity(Intent(context, CheckoutActivity::class.java))
                }

                ItemTouchHelper(ViewHelpers.SwipeToRemove { position ->
                    val product = cartProductAdapter.differ.currentList[position]

                    deleteProductDialog(product)

                }).attachToRecyclerView(rvCartProducts)

            }
        }

    }

    private fun setupRecycleView() {
        binding.rvCartProducts.apply {
            adapter = cartProductAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun deleteProductDialog(roomCart: RoomCart) {
        AlertDialog.Builder(context).apply {
            setNegativeButton("No") { d, i ->
                cartProductAdapter.notifyDataSetChanged()
                d.dismiss()
            }
            setPositiveButton("yes") { d, i ->
                cartViewModel.deleteCartProduct(roomCart.id)
                d.dismiss()
                Toast.makeText(requireContext(), "product deleted successfully", Toast.LENGTH_SHORT).show()
            }

            setTitle("Do you want remove this product?")
        }.create().show()
    }

    private fun messageDialog() {
        AlertDialog.Builder(context).apply {
            setNeutralButton("ok") { d, i ->
                d.dismiss()
            }
            setTitle("sorry, no more stock from this product \uD83D\uDE1E")
        }.create().show()
    }


}