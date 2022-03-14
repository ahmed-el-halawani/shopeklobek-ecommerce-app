package com.stash.shopeklobek.ui.checkout.finish

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.error.OnError
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentFinishBinding
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.model.utils.RoomAddOrderErrors
import com.stash.shopeklobek.ui.checkout.CheckoutBaseFragment
import com.stash.shopeklobek.ui.checkout.PaymentMethodsEnum
import com.stash.shopeklobek.utils.getPrice
import com.stash.shopeklobek.utils.toCurrency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FinishFragment : CheckoutBaseFragment<FragmentFinishBinding>(FragmentFinishBinding::inflate) {

    private val finishViewModel by lazy {
        FinishViewModel.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        finishViewModel.loading.observe(viewLifecycleOwner) {
            if (it)
                showLoading()
            else
                hideLoading()
        }

        paypal()

        // order details
        binding.run {
            mainViewModel.run {
                tvTotalProductsPrice.text = cartProducts.getPrice().toCurrency(requireContext())
                tvDelivary.text = shipping.toCurrency(requireContext())
                tvDiscount.text = showDiscountRow(discount?.value)?.toCurrency(requireContext())
                tvTotal.text = calculateTotalPrice().toCurrency(requireContext())
            }
        }

        //paymentMethod
        binding.run {
            mainViewModel.run {
                cvCash.visibility = View.GONE
                cvPaypal.visibility = View.GONE

                when (selectedPaymentMethods) {
                    PaymentMethodsEnum.Cash -> {
                        cvCash.visibility = View.VISIBLE
                    }
                    PaymentMethodsEnum.Paypal -> {
                        cvPaypal.visibility = View.VISIBLE
                    }
                }
            }
        }

        //address
        binding.run {
            mainViewModel.run {
                cvCurrentLocation.run {
                    address = selectedAddress?.generateAddressLine()
                    title = selectedAddress?.city
                    refresh()
                }
            }
        }


        //button confirm
        binding.btnConfirm.setOnClickListener {
            finishViewModel.loading.postValue(true)
            lifecycleScope.launch {
                when (mainViewModel.selectedPaymentMethods) {
                    PaymentMethodsEnum.Cash -> confirmIt()
                    PaymentMethodsEnum.Paypal -> {
                        mainViewModel.startCheck()
                    }
                }
            }
        }
    }

    private suspend fun confirmIt() {
        when (val res = mainViewModel.confirm()) {
            is Either.Error -> finishViewModel.loading.postValue(false).also {
                when(res.errorCode) {
                    RepoErrors.NoLoginCustomer ->
                        Toast.makeText(
                            context,
                            getString(R.string.u_havent_login_yet),
                            Toast.LENGTH_SHORT
                        ).show()
                    else -> Toast.makeText(
                        context,
                        getString(R.string.someThing_wrong_happened),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            is Either.Success -> finishViewModel.loading.postValue(false).also {
                messageDialog()
            }
        }
    }

    private fun paypal() {
        PayPalCheckout.registerCallbacks(
            onApprove = OnApprove { approval ->
                approval.orderActions.capture { _ ->
                    CoroutineScope(Dispatchers.Main).launch {
                        confirmIt()
                    }
                }
            },

            onCancel = OnCancel {
                finishViewModel.loading.postValue(false)
                Toast.makeText(
                    context,
                    "canceled",
                    Toast.LENGTH_SHORT
                ).show()
            },

            onError = OnError { errorInfo ->
                finishViewModel.loading.postValue(false)
                Toast.makeText(
                    context,
                    getString(R.string.someThing_wrong_happened),
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("paypal_onError", "paypal: " + errorInfo)
            }
        )
    }


    private fun showDiscountRow(value: String? = null): Double? {
        binding.run {
            return if (value == null) {
                rowDiscount.visibility = View.GONE
                null
            } else {
                rowDiscount.visibility = View.VISIBLE
                value.toDouble()
            }
        }
    }

    fun calculateTotalPrice() =
        ((mainViewModel.discount?.value?.toDouble()) ?: 0.0) + mainViewModel.cartProducts.getPrice() + mainViewModel.shipping

    private fun messageDialog() {
        AlertDialog.Builder(context).apply {
            setNeutralButton(getString(R.string.ok)) { d, i ->
                activity?.finish()
            }
            setTitle(getString(R.string.done_order_message))
        }.create().show()
    }
}