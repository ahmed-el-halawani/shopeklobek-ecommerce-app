package com.stash.shopeklobek.ui.authentication.register

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.stash.shopeklobek.databinding.FragmentHomeBinding
import com.stash.shopeklobek.databinding.FragmentRegisterBinding
import com.stash.shopeklobek.ui.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().popBackStack()
    }
}