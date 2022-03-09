package com.stash.shopeklobek.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.orhanobut.hawk.Hawk
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.FragmentSettingsBinding
import com.stash.shopeklobek.model.shareprefrances.CurrenciesEnum
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.MainActivity

import com.stash.shopeklobek.utils.ViewHelpers.setAppLocale

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        checkLanguage()
        checkCurrency()
        setLanguageBtnListeners()
        setCurrencyBtnListeners()
        binding.btnAddress.setOnClickListener {
            findNavController().navigate(R.id.action_nav_settings_to_addAddressFragment2,Bundle().apply {
                putBoolean("isDefault",true)
            })
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

    val vm by lazy {
        SettingsViewModel.create(this)
    }

    private fun setLanguageBtnListeners() {
        binding.languageGroup.setOnCheckedChangeListener { _, i ->
            if (i == binding.btnEArabic.id) {
                Hawk.put("language", "ar")
                setAppLocale(requireActivity(),resources)
            } else {
                Hawk.put("language", "en")
                setAppLocale(requireActivity(),resources)
            }
        }
    }

    private fun checkLanguage() {
        val language: String? = Hawk.get<String>("language")
        if (language == "ar") binding.languageGroup.check(binding.btnEArabic.id)
        else binding.languageGroup.check(binding.btnEnglish.id)
    }


}