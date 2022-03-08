package com.stash.shopeklobek.ui.checkout.paymethod

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentPaymentScreenBinding
import com.stash.shopeklobek.ui.checkout.CheckoutBaseFragment
import com.stash.shopeklobek.ui.checkout.PaymentMethodsEnum

class PaymentMethod : CheckoutBaseFragment<FragmentPaymentScreenBinding>(FragmentPaymentScreenBinding::inflate) {

    val paymentMethodViewModel by lazy {
        PaymentMethodViewModel.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rbPaypal.setOnClickListener (::onRadioButtonSelected)
            rbCash.setOnClickListener (::onRadioButtonSelected)
        }

        binding.rgMethods.setOnCheckedChangeListener { _, checkedId ->
            mainViewModel.selectedPaymentMethods = when(checkedId){
                R.id.rbPaypal-> PaymentMethodsEnum.Paypal
                R.id.rbCash-> PaymentMethodsEnum.Cash
                else ->  PaymentMethodsEnum.Cash
            }
        }

        binding.btnToFinish.setOnClickListener {
            mainViewModel.pageIndexLiveData.postValue(2)
            findNavController().navigate(R.id.action_paymentMethod_to_finish)
        }





    }


    private fun onRadioButtonSelected(view:View){
        binding.rgMethods.check(view.id)
    }

}