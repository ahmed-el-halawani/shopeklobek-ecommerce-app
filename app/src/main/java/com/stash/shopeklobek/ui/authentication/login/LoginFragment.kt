package com.stash.shopeklobek.ui.authentication.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.stash.shopeklobek.databinding.FragmentLoginBinding
import com.stash.shopeklobek.ui.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    var userEmail: String? = null
    var userPassword: String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vm by lazy {
            LoginViewModel.create(this)
        }

        binding.btnLogin.setOnClickListener {
            if (validteForm()) {
                vm.getData(userEmail!!)
                vm.loginSuccess.observe(viewLifecycleOwner) {
                    if (it!!) {

                        Toast.makeText(requireContext(), "Logged in sccesfully", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
        binding.btnAuto.setOnClickListener {
            vm.getData("ibraheem@gmail.com")
            vm.loginSuccess.observe(viewLifecycleOwner) {
                if (it!!) {

                    Toast.makeText(requireContext(), "Logged in sccesfully", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun validteForm(): Boolean {
        userEmail = binding.edtEmail.text.toString()
        userPassword = binding.edtPassword.text.toString()
        if (userEmail!!.isEmpty()) {
            binding.edtEmail.requestFocus()
            binding.edtEmail.error = "Required"
            return false
        }
        if (userPassword!!.isEmpty()) {
            binding.edtPassword.requestFocus()
            binding.edtPassword.error = "Required"
            return false
        }

        return true

    }
}