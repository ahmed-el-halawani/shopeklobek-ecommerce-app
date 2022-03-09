package com.stash.shopeklobek.ui.checkout.paymethod

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentPaymentScreenBinding
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.ui.checkout.CheckoutBaseFragment
import com.stash.shopeklobek.ui.checkout.PaymentMethodsEnum
import com.stash.shopeklobek.utils.ViewHelpers.hideSoftKeyboard
import com.stash.shopeklobek.utils.getPrice
import com.stash.shopeklobek.utils.toCurrency

class PaymentMethod :
    CheckoutBaseFragment<FragmentPaymentScreenBinding>(FragmentPaymentScreenBinding::inflate) {

    val paymentMethodViewModel by lazy {
        PaymentMethodViewModel.create(
            this
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        binding.run {
            rbPaypal.setOnClickListener(
                ::onRadioButtonSelected
            )
            rbCash.setOnClickListener(
                ::onRadioButtonSelected
            )
            etFromTime.doOnTextChanged { _, _, _, _ ->
                icVoucherState.setImageResource(
                    R.drawable.ic_baseline_circle_24
                )
            }
        }

        binding.rgMethods.setOnCheckedChangeListener { _, checkedId ->
            mainViewModel.selectedPaymentMethods =
                when (checkedId) {
                    R.id.rbPaypal -> PaymentMethodsEnum.Paypal
                    R.id.rbCash -> PaymentMethodsEnum.Cash
                    else -> PaymentMethodsEnum.Cash
                }
        }


        binding.run {

            mainViewModel.fixedDiscountLiveData.observe(
                viewLifecycleOwner
            ) {
                loadingDiscountCode.visibility =
                    View.GONE

                when (it) {
                    is Either.Error -> {
                        etFromTime.error = getString(R.string.Wrong_Code)
                        icVoucherState.setImageResource(
                            R.drawable.ic_wrong
                        )
                    }
                    is Either.Success -> {
                        icVoucherState.setImageResource(
                            R.drawable.ic_done
                        )
                    }
                }

                showDiscountRow(
                    mainViewModel.priceRule?.value
                )
                calculateTotalPrice()
            }

            tvDelivary.text =
                mainViewModel.shipping.toCurrency()

            btnApply.setOnClickListener {
                hideSoftKeyboard(
                    requireActivity()
                )
                if (etFromTime.text.isNullOrBlank()) {
                    etFromTime.error =
                        getString(R.string.no_discount_added)
                } else {
                    etFromTime.error =
                        null
                    loadingDiscountCode.visibility =
                        View.VISIBLE
                    mainViewModel.addDiscount(
                        etFromTime.text.toString()
                    )
                }
            }
        }

        binding.run {
            tvTotalProductsPrice.text = mainViewModel.cartProducts.getPrice().toCurrency()
        }

        binding.btnToFinish.setOnClickListener {
            mainViewModel.pageIndexLiveData.postValue(2)
            findNavController().navigate(
                R.id.action_paymentMethod_to_finish
            )
        }


    }

    private fun showDiscountRow(
        value: String? = null
    ) {
        binding.run {
            if (value == null) {
                rowDiscount.visibility =
                    View.GONE
            } else {
                rowDiscount.visibility =
                    View.VISIBLE
                tvDiscount.text =
                    value.toCurrency()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun calculateTotalPrice() {
        binding.run {
            tvTotal.text =
                (((mainViewModel.priceRule?.value?.toDouble()) ?: 0.0) + mainViewModel
                    .cartProducts.getPrice() + mainViewModel.shipping).toCurrency()
        }
    }

    private fun onRadioButtonSelected(
        view: View
    ) {
        binding.rgMethods.check(
            view.id
        )
    }

}