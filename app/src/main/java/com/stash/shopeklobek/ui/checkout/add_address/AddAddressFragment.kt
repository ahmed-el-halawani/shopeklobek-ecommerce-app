package com.stash.shopeklobek.ui.checkout.add_address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.stash.shopeklobek.databinding.FragmentAddAddressBinding
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.ui.checkout.CheckoutBaseFragment
import kotlinx.coroutines.launch

class AddAddressFragment : Fragment() {

    val binding by lazy {
        FragmentAddAddressBinding.inflate(layoutInflater)
    }

    val vm by lazy {
        AddAddressViewModel.create(this)
    }

    val message = "Required Field"

    val args:AddAddressFragmentArgs? by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareListener()
        validate()

        if(args?.isDefault == true){
            binding.rbSetAsDefault.visibility = View.GONE
            binding.rbSetAsDefault.isChecked = true
        }

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