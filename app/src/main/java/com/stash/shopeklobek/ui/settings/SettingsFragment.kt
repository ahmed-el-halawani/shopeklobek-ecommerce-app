package com.stash.shopeklobek.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.orhanobut.hawk.Hawk
import com.stash.shopeklobek.databinding.FragmentSettingsBinding
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.MainActivity

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLanguage()


       setBtnListeners()
    }

    private fun setBtnListeners() {
        binding.languageGroup.setOnCheckedChangeListener { _, i ->
            if (i == binding.btnEArabic.id) {
                Hawk.put("language", "ar")
                requireActivity().finish()
                requireActivity().startActivity(Intent(requireActivity(),MainActivity::class.java))            }
            else {
                requireActivity().finish()
                requireActivity().startActivity(Intent(requireActivity(),MainActivity::class.java))
            }
        }
    }

    private fun checkLanguage() {
        val language: String? = Hawk.get<String>("language")
        if (language == "ar") binding.languageGroup.check(binding.btnEArabic.id)
        else binding.languageGroup.check(binding.btnEnglish.id)
    }


}