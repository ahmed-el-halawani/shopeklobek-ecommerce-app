package com.stash.shopeklobek.ui.authentication.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentLoginBinding
import com.stash.shopeklobek.ui.BaseFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    var userEmail: String? = null
    var userPassword: String? = null

    val vm by lazy {
        LoginViewModel.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        vm.AuthRepo.settingsPreferences.getSettingsLiveData().observe(viewLifecycleOwner){
            if(it.customer!=null){
                findNavController().popBackStack()
            }
        }


        binding.btnLogin.setOnClickListener {
            binding.progress.visibility=View.VISIBLE
            if (validteForm()) {
                vm.getData(userEmail!!,userPassword!!)
                vm.loginSuccess.observe(viewLifecycleOwner) {
                    if (it!!) {
                        binding.progress.visibility=View.GONE
                       Toast.makeText(requireContext(),"Loggedin Successfully",Toast.LENGTH_LONG).show()

                    }else   Toast.makeText(requireContext(),"please enter correct email or password",Toast.LENGTH_LONG).show()

                }
            }
        }
        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_nav_login_to_nav_register)
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