package com.stash.shopeklobek.ui.authentication.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.stash.shopeklobek.databinding.FragmentRegisterBinding
import com.stash.shopeklobek.ui.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    var userName: String? = null
    var userEmail: String? = null
    var userPassword: String? = null
    var userConfirmPassword: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRegister.setOnClickListener {
            if (validateFrem())
                Toast.makeText(requireContext(), "Registered succssfully", Toast.LENGTH_LONG).show()
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