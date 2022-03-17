package com.stash.shopeklobek.ui.authentication.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentRegisterBinding
import com.stash.shopeklobek.model.entities.Customer
import com.stash.shopeklobek.model.entities.CustomerModel
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.SignUpErrors
import com.stash.shopeklobek.ui.BaseFragment
import kotlinx.coroutines.launch

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
        vm.authenticationRepo.settingsPreferences.getSettingsLiveData()
            .observe(viewLifecycleOwner) {
                if (it.customer != null) {
                    findNavController().popBackStack()
                }
            }
        binding.btnRegister.setOnClickListener {

            if (validateFrem()) {
                binding.progress.visibility = View.VISIBLE
                val customer = CustomerModel(
                    Customer(
                        firstName = firstName,
                        lastName = userPassword,
                        email = userEmail,
                        password = userPassword,
                        passwordConfirmation = userConfirmPassword
                    )
                )


                lifecycleScope.launch {

                    when (val response = vm.postData(customer)) {
                        is Either.Error -> vm.signupSuccess.postValue(false).also {
                            when (response.errorCode) {
                                SignUpErrors.NoInternetConnection -> {
                                    Toast.makeText(
                                        context, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT
                                    ).show()
                                }
                                SignUpErrors.ServerError -> {
                                    Toast.makeText(
                                        context,
                                        getString(R.string.server_error),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                SignUpErrors.EmptyBody -> {}
                                SignUpErrors.NullValue -> {}
                                SignUpErrors.NoLoginCustomer -> {}
                                SignUpErrors.EmailAlreadyExist -> {
                                    binding.edtEmail.error = getString(R.string.email_already_exist)
                                    Toast.makeText(
                                        context, getString(R.string.email_already_exist),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        is Either.Success -> {
                            vm.signupSuccess.postValue(true)
                        }
                    }
                }

                vm.signupSuccess.observe(viewLifecycleOwner) {
                    if (it!!) {
                        binding.progress.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.registered_successfully),
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        binding.progress.visibility = View.GONE

                    }
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
            binding.edtEmail.error = getString(R.string.required)
            return false
        }
        if (firstName!!.isEmpty()) {
            binding.edtFirstName.requestFocus()
            binding.edtFirstName.error = getString(R.string.required)
            return false
        }

        if (userPassword!!.isEmpty()) {
            binding.edtPassword.requestFocus()
            binding.edtPassword.error = getString(R.string.required)
            return false
        }
        if (userPassword!!.length < 8) {
            binding.edtPassword.requestFocus()
            binding.edtPassword.error = getString(R.string.more_than_eight)
            return false
        }
        if (userConfirmPassword!!.length < 8) {
            binding.edtConfirm.requestFocus()
            binding.edtConfirm.error =  getString(R.string.more_than_eight)
            return false
        }
        if (userConfirmPassword!!.isEmpty()) {
            binding.edtConfirm.requestFocus()
            binding.edtConfirm.error = getString(R.string.required)
            return false
        }
        if (!userPassword.equals(userConfirmPassword)) {
            binding.edtConfirm.requestFocus()
            binding.edtConfirm.error = getString(R.string.doesnt_exist)
            return false
        }

        return true
    }
}