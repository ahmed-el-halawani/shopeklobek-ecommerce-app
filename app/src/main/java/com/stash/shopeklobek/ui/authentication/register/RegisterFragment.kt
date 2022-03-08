package com.stash.shopeklobek.ui.authentication.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentRegisterBinding
import com.stash.shopeklobek.model.entities.Customer
import com.stash.shopeklobek.model.entities.CustomerModel
import com.stash.shopeklobek.ui.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    var firstName: String? = null
    var userEmail: String? = null
    var userPassword: String? = null
    var userConfirmPassword: String? = null
//    var addresses: List<Address>? = listOf()


    private val vm by lazy {
        RegisterViewModel.create(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            if (validateFrem()) {

                val customer = CustomerModel(
                    Customer(
                        firstName = firstName,
                        lastName = userPassword,
                        email = userEmail,
                        password = userPassword,
                        passwordConfirmation = userConfirmPassword
                    )
                )
                vm.postData(customer)
                vm.signupSuccess.observe(viewLifecycleOwner) {
                    if (it!!) {
                        Toast.makeText(
                            requireContext(),
                            "Registered succssfully",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                    else  Toast.makeText(
                        requireContext(),
                        "Unsuccessfull Register",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        binding.tvlogin.setOnClickListener {
            findNavController().navigate(R.id.action_nav_register_to_nav_login)
        }
    }

    private fun validateFrem(): Boolean {
        firstName = binding.edtFirstName.text.toString()
        userEmail = binding.edtEmail.text.toString()
        userPassword = binding.edtPassword.text.toString()
        userConfirmPassword = binding.edtConfirm.text.toString()
        if (userEmail!!.isEmpty()) {
            binding.edtEmail.requestFocus()
            binding.edtEmail.error = "Required"
            return false
        }
        if (firstName!!.isEmpty()) {
            binding.edtFirstName.requestFocus()
            binding.edtFirstName.error = "Required"
            return false
        }

        if (userPassword!!.isEmpty()) {
            binding.edtPassword.requestFocus()
            binding.edtPassword.error = "Required"
            return false
        }
        if (userPassword!!.length<8) {
            binding.edtPassword.requestFocus()
            binding.edtPassword.error = "password must be more than 8 characters"
            return false
        }
        if (userConfirmPassword!!.length<8) {
            binding.edtConfirm.requestFocus()
            binding.edtConfirm.error = "password must be more than 8 characters"
            return false
        }
        if (userConfirmPassword!!.isEmpty()) {
            binding.edtConfirm.requestFocus()
            binding.edtConfirm.error = "Required"
            return false
        }
        if (!userPassword.equals(userConfirmPassword)) {
            binding.edtConfirm.requestFocus()
            binding.edtConfirm.error = "password doesn't match"
            return false
        }

        return true
    }
}