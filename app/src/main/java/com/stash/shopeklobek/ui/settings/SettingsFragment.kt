package com.stash.shopeklobek.ui.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.orhanobut.hawk.Hawk
import com.stash.shopeklobek.databinding.FragmentSettingsBinding
import com.stash.shopeklobek.model.shareprefrances.CurrenciesEnum
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.MainActivity
import com.stash.shopeklobek.ui.authentication.login.LoginViewModel

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLanguage()
        checkCurrency()
        setLanguageBtnListeners()
        setCurrencyBtnListeners()
    }

    private fun checkCurrency() {
        when(vm.productRepo.getSettings().currancy.idEnum){
            CurrenciesEnum.EGP ->  binding.currencyGroup.check(binding.btnEGP.id)
            CurrenciesEnum.USD -> binding.currencyGroup.check(binding.btnUSD.id)
            CurrenciesEnum.EUR -> binding.currencyGroup.check(binding.btnEURO.id)
        }
    }

    private fun setCurrencyBtnListeners() {
        binding.currencyGroup.setOnCheckedChangeListener{_,i->
            if (i==binding.btnEGP.id){
                vm.getCurrency(CurrenciesEnum.EGP)
            }
              if (i==binding.btnUSD.id){
                vm.getCurrency(CurrenciesEnum.USD)
            }
            if(i==binding.btnEURO.id)  {
                vm.getCurrency(CurrenciesEnum.EUR)
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
                requireActivity().finish()
                requireActivity().startActivity(Intent(requireActivity(), MainActivity::class.java))
            } else {
                Hawk.put("language", "en")
                requireActivity().finish()
                requireActivity().startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
        }

    }

    private fun checkLanguage() {
        val language: String? = Hawk.get<String>("language")
        if (language == "ar") binding.languageGroup.check(binding.btnEArabic.id)
        else binding.languageGroup.check(binding.btnEnglish.id)
    }


}