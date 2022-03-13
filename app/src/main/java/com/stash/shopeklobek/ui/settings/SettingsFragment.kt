package com.stash.shopeklobek.ui.settings

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.orhanobut.hawk.Hawk
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentSettingsBinding
import com.stash.shopeklobek.model.shareprefrances.CurrenciesEnum
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.utils.ViewHelpers.setAppLocale
import kotlinx.coroutines.launch

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkAddress()
        checkLanguage()
        checkCurrency()
        setLanguageBtnListeners()
        setCurrencyBtnListeners()
        binding.btnAddressInclude.btn.setOnClickListener {
            findNavController().navigate(
                R.id.action_nav_settings_to_addAddressFragment2,
                Bundle().apply {
                    putBoolean("isDefault", true)
                })
        }
    }

    private fun checkAddress() {

        vm.productRepo.run {
            getSettingsLiveData().observe(viewLifecycleOwner) {
                binding.run {
                    if (it.customer == null) {
                        addressGroup.visibility = View.GONE
                    } else {
                        addressGroup.visibility = View.VISIBLE
                        lifecycleScope.launch {
                            when (val res = getAddress()) {
                                is Either.Error -> addressCardView.visibility = View.GONE
                                is Either.Success -> res.data.getDefaultOrFirst().run {
                                    if (this == null)
                                        addressCardView.visibility = View.GONE
                                    else {
                                        addressCardView.run {
                                            title = city
                                            address = generateAddressLine()
                                            refresh()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkCurrency() {
        when (vm.productRepo.getSettings().currancy.idEnum) {
            CurrenciesEnum.EGP -> binding.currencyGroup.check(binding.btnEGP.id)
            CurrenciesEnum.USD -> binding.currencyGroup.check(binding.btnUSD.id)
            CurrenciesEnum.SAR -> binding.currencyGroup.check(binding.btnEURO.id)
        }
    }

    private fun setCurrencyBtnListeners() {
        binding.currencyGroup.setOnCheckedChangeListener { _, i ->
            if (i == binding.btnEGP.id) {
                vm.getCurrency(CurrenciesEnum.EGP)
            }
            if (i == binding.btnUSD.id) {
                vm.getCurrency(CurrenciesEnum.USD)
            }
            if (i == binding.btnEURO.id) {
                vm.getCurrency(CurrenciesEnum.SAR)
            }
        }


    }

  private  val vm by lazy {
        SettingsViewModel.create(this)
    }

    private fun setLanguageBtnListeners() {
        binding.languageGroup.setOnCheckedChangeListener { _, i ->
            if (i == binding.btnEArabic.id) {
                Hawk.put("language", "ar")
                setAppLocale(requireActivity(), resources)
            } else {
                Hawk.put("language", "en")
                setAppLocale(requireActivity(), resources)
            }
        }
    }

    private fun checkLanguage() {
        val language: String? = Hawk.get<String>("language")
        if (language == "ar") binding.languageGroup.check(binding.btnEArabic.id)
        else binding.languageGroup.check(binding.btnEnglish.id)
    }


}