package com.stash.shopeklobek.ui.checkout.paymethod

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentHomeBinding
import com.stash.shopeklobek.databinding.FragmentLoginBinding
import com.stash.shopeklobek.databinding.FragmentPaymentScreenBinding
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.checkout.CheckoutBaseFragment

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

        binding.rgMethods.setOnCheckedChangeListener { group, checkedId ->
            println(checkedId)
        }

        binding.btnToFinish.setOnClickListener {
            viewmodel.pageLiveData.postValue(2)
            findNavController().navigate(R.id.action_paymentMethod_to_finish)
        }
    }


    fun onRadioButtonSelected(view:View){
        binding.rgMethods.check(view.id)
    }

}