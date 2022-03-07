package com.stash.shopeklobek.ui.authentication.login.completeLogin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.stash.shopeklobek.databinding.CompleteLoginFragmentBinding
import com.stash.shopeklobek.ui.BaseFragment

class CompleteLoginFragment : BaseFragment<CompleteLoginFragmentBinding>(
    CompleteLoginFragmentBinding::inflate
) {

   private var userPass: String? = null
    private val vm by lazy {
        CompleteLoginViewModel.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            userPass = binding.edtpassword.text.toString()

            if (vm.authenticationRepo.settingsPreferences.getSettings().customer!!.lastName.equals(
                    userPass
                )
            ) {
                findNavController().popBackStack()
                findNavController().popBackStack()
                Toast.makeText(requireContext(), "Logged in successfully", Toast.LENGTH_LONG).show()
            }
            else Toast.makeText(requireContext(), "please enter correct email or password", Toast.LENGTH_LONG).show()
        }

    }
}