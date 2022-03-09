package com.stash.shopeklobek.ui.checkout.add_address

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.stash.shopeklobek.databinding.FragmentAddAddressBinding
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.ui.checkout.CheckoutBaseFragment
import kotlinx.coroutines.launch

class AddAddressFragment : CheckoutBaseFragment<FragmentAddAddressBinding>(FragmentAddAddressBinding::inflate) {


    val vm by lazy {
        AddAddressViewModel.create(this)
    }

    val message = "Required Field"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareListener()
        validate()
        binding.apply {
            btnSave.setOnClickListener {
                if (vm.isValid) {
                    Toast.makeText(context, "add address", Toast.LENGTH_SHORT).show()

                    lifecycleScope.launch {
                        when (val res = vm.addAddress()) {
                            is Either.Error -> when (res.errorCode) {
                                RepoErrors.NoInternetConnection -> {}
                                RepoErrors.ServerError -> {}
                                RepoErrors.EmptyBody -> {

                                }
                            }
                            is Either.Success -> {
                                findNavController().popBackStack()
                            }
                        }
                    }
                } else {
                    vm.setErrors()
                    Toast.makeText(context, "required fields", Toast.LENGTH_SHORT).show()
                }
            }

            btnCancel.setOnClickListener {
                findNavController().popBackStack()
            }
        }


    }

    private fun prepareListener() {
        binding.apply {

            etAddressLine.doOnTextChanged { s, _, _, _ ->
                vm.addressLiveData.value = vm.addressSource.apply {
                    vm.addressSource = this.copy(address = s.toString())
                }
            }

            etCity.doOnTextChanged { s, start, before, count ->
                vm.addressLiveData.value = vm.addressSource.apply {
                    vm.addressSource = this.copy(
                        city = s.toString()
                    )
                }
            }

            etFirstName.doOnTextChanged { s, start, before, count ->
                vm.addressLiveData.value = vm.addressSource.apply {
                    vm.addressSource = this.copy(
                        firstName = s.toString()
                    )
                }
            }

            etLastName.doOnTextChanged { s, start, before, count ->
                vm.addressLiveData.value = vm.addressSource.apply {
                    vm.addressSource = this.copy(
                        lastName = s.toString()
                    )
                }
            }

            etPhone.doOnTextChanged { s, start, before, count ->
                vm.addressLiveData.value = vm.addressSource.apply {
                    vm.addressSource = this.copy(
                        phone = s.toString()
                    )
                }
            }

            rbSetAsDefault.setOnCheckedChangeListener { _, b ->
                vm.isDefault = b
            }
        }
    }


    fun validate() {
        vm.addressLiveData.observe(viewLifecycleOwner) {
            var isValid = true
            binding.apply {

                if (vm.addressSource.address.isNullOrBlank()) {
                    isValid = false
                    etAddressLineLayout.error = if (vm.setErrors) message else null
                } else {
                    etAddressLineLayout.error = null
                }

                if (vm.addressSource.city.isNullOrBlank()) {
                    isValid = false
                    etCityLayout.error = if (vm.setErrors) message else null
                } else {
                    etCityLayout.error = null
                }

                if (vm.addressSource.firstName.isNullOrBlank()) {
                    isValid = false
                    etFirstNameLayout.error = if (vm.setErrors) message else null
                } else {
                    etFirstNameLayout.error = null
                }

                if (vm.addressSource.phone.isNullOrBlank()) {
                    isValid = false
                    etPhoneLayout.error = if (vm.setErrors) message else null
                } else {
                    etPhoneLayout.error = null
                }
            }
            vm.isValid = isValid
        }
    }


}