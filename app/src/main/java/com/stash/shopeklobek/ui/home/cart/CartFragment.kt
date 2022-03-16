package com.stash.shopeklobek.ui.home.cart

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.stash.shopeklobek.utils.NetworkingHelper.hasInternet
import com.stash.shopeklobek.utils.ViewHelpers
import kotlinx.coroutines.launch

class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private val cartProductAdapter by lazy {
        CartProductsAdapter().apply {
            setOnDecrementClickListener {
                if (it.count > 1) {
                    lifecycleScope.launch {
                        when (cartViewModel.updateCartProduct(it.copy(count = it.count - 1))) {
                            is Either.Error -> {
                                Toast.makeText(
                                    context,
                                    getString(R.string.someThing_wrong_happened),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is Either.Success -> Unit
                        }
                    }
                } else {
                    deleteProductDialog(it)
                }
            }

            setOnIncrementClickListener {

                if ((it.variant()?.quantity) != null && it.count < it.variant()?.quantity!!) {

                    lifecycleScope.launch {
                        when (cartViewModel.updateCartProduct(
                            it.copy(
                                count = it.count + 1
                            )
                        )) {
                            is Either.Error -> {
                                Toast.makeText(
                                    context,
                                    getString(R.string.someThing_wrong_happened),
                                    Toast.LENGTH_SHORT
                                ).show()
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupRecycleView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            cartViewModel.productRepo.getSettingsLiveData().observe(viewLifecycleOwner) {
                if (it.customer == null) {
                    cvCartDetails.visibility = View.GONE
                    findNavController().navigate(R.id.action_cartFragment_to_completeAction)
                } else {
                    if (cartViewModel.isNeedToRebuild(it.currancy)) {
                        setupRecycleView()
                    }

                    if (cartViewModel.isNeedToRefresh(it.customer)) {
                        cartViewModel.getCartProductsEither()
                    }

                }


                cartViewModel.cartLiveData.observe(viewLifecycleOwner) { roomCarts ->
                    if (roomCarts.isEmpty()) {
                        cvCartDetails.visibility = View.GONE
                        emptyCartGroup.visibility = View.VISIBLE
                    } else {
                        cvCartDetails.visibility = View.VISIBLE
                        emptyCartGroup.visibility = View.GONE
                    }

                    cartProductAdapter.differ.submitList(roomCarts)

                    var price = 0.0
                    var count = 0
                    roomCarts.forEach {
                        price += (it.variant()?.price?.toDouble() ?: 0.0) * it.count
                        count += it.count
                    }

                    tvTotalProductsPrice.text = CurrencyUtil.convertCurrency(
                        price.toString(), requireContext()
                    )
                    tvTotalItems.text = count.toString()
                }
            }



            btnProceedToCheckout.setOnClickListener {
                if (hasInternet(requireContext().applicationContext))
                    startActivity(Intent(context, CheckoutActivity::class.java))
                else
                    Toast.makeText(
                        context, getString(R.string.no_internet_connection), Toast
                            .LENGTH_SHORT
                    ).show()
            }

            ItemTouchHelper(ViewHelpers.SwipeToRemove { position ->
                val product = cartProductAdapter.differ.currentList[position]

                deleteProductDialog(product)

            }).attachToRecyclerView(rvCartProducts)

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
                Toast.makeText(
                    requireContext(),
                    getString(R.string.product_deleted),
                    Toast.LENGTH_SHORT
                ).show()
            }

            setTitle(getString(R.string.do_u_want_remove_product))
        }.create().show()
    }

    private fun messageDialog() {
        AlertDialog.Builder(context).apply {
            setNeutralButton("ok") { d, i ->
                d.dismiss()
            }
            setTitle(getString(R.string.no_more_stock))
        }.create().show()
    }


}