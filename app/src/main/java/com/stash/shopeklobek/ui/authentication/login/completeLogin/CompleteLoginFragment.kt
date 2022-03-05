package com.stash.shopeklobek.ui.authentication.login.completeLogin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Toast
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.CompleteLoginFragmentBinding
import com.stash.shopeklobek.databinding.CompleteProfileFragmentBinding
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.ui.BaseFragment
import com.stash.shopeklobek.ui.authentication.register.RegisterViewModel

class CompleteLoginFragment : BaseFragment<CompleteLoginFragmentBinding>(
    CompleteLoginFragmentBinding::inflate) {

var userPass:String?=null
    private val vm by lazy {
        CompleteLoginViewModel.create(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            userPass=binding.edtpassword.text.toString()
            Log.d("yaraab",""+vm.authenticationRepo.settingsPreferences.getSettings().customer!!.lastName)
            if (vm.authenticationRepo.settingsPreferences.getSettings().customer!!.lastName.equals(userPass)){
                Toast.makeText(requireContext(),"Logged in successfully",Toast.LENGTH_LONG).show()
            }
        }
        vm.authenticationRepo.settingsPreferences.getSettings()
    }
}