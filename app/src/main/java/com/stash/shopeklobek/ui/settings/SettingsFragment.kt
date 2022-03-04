package com.stash.shopeklobek.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.stash.shopeklobek.databinding.FragmentSettingsBinding
import com.stash.shopeklobek.ui.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.languageGroup.setOnCheckedChangeListener { radioGroup, i ->
            if (i==binding.btnEArabic.id){
                Toast.makeText(requireContext(), "arr", Toast.LENGTH_SHORT).show()

            }
        }
    }


}