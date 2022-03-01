package com.stash.shopeklobek.ui.authentication.register

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.stash.shopeklobek.databinding.FragmentRegisterBinding
import com.stash.shopeklobek.model.entities.Customer
import com.stash.shopeklobek.model.entities.CustomerModel
import com.stash.shopeklobek.ui.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    var userName: String? = null
    var userEmail: String? = null
    var userPassword: String? = null
    var userConfirmPassword: String? = null
    val viewModel:RegisterViewModel?=null

    private val vm by lazy{
        RegisterViewModel.create(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {

            if (validateFrem()){
                val customer = CustomerModel(
                    Customer(
                        firstName = userName, email = userEmail, password = userPassword, passwordConfirmation = userConfirmPassword
                    ),null
                )
                Log.d("cus",""+customer.customer?.firstName)
                Log.d("cus",""+customer.customer?.email)
                Log.d("cus",""+customer.customer?.password)
                Log.d("cus",""+customer.customer?.passwordConfirmation)
                vm.postData(customer)
//                vm.postData()
                vm.signupSuccess.observe(viewLifecycleOwner) {
                    if (it!!) {
                        Toast.makeText(requireContext(), "Registered succssfully", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }

    private fun validateFrem(): Boolean {
        userName = binding.edtuserName.text.toString()
        userEmail = binding.edtEmail.text.toString()
        userPassword = binding.edtPassword.text.toString()
        userConfirmPassword = binding.edtConfirm.text.toString()
        if (userEmail!!.isEmpty()) {
            binding.edtEmail.requestFocus()
            binding.edtEmail.setError("Required")
            return false
        }
        if (userName!!.isEmpty()) {
            binding.edtuserName.requestFocus()
            binding.edtuserName.setError("Required")
            return false
        }

        if (userPassword!!.isEmpty()) {
            binding.edtPassword.requestFocus()
            binding.edtPassword.setError("Required")
            return false
        }
        if (userConfirmPassword!!.isEmpty()) {
            binding.edtConfirm.requestFocus()
            binding.edtConfirm.setError("Required")
            return false
        }
        if (!userPassword.equals(userConfirmPassword)) {
            binding.edtConfirm.requestFocus()
            binding.edtConfirm.setError("password doesn't match")
            return false
        }
        return true
    }
}