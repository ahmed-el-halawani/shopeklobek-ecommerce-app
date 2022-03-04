package com.stash.shopeklobek.ui.authentication.register.completeProfile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stash.shopeklobek.R
import com.stash.shopeklobek.databinding.CompleteProfileFragmentBinding
import com.stash.shopeklobek.databinding.FragmentRegisterBinding
import com.stash.shopeklobek.ui.BaseFragment

class CompleteProfileFragment : BaseFragment<CompleteProfileFragmentBinding>(CompleteProfileFragmentBinding::inflate) {

    companion object {
        fun newInstance() = CompleteProfileFragment()
    }

    private lateinit var viewModel: CompleteProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.complete_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CompleteProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}