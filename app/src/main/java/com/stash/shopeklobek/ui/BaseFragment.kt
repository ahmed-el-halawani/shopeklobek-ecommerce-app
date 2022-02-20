package com.stash.shopeklobek.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.stash.shopeklobek.utils.ILoading

abstract class BaseFragment<T : ViewBinding>(val viewBindingInflater:(LayoutInflater)->T): Fragment(),
    ILoading {

    val mainActivity by lazy{
        this.activity as MainActivity
    }

    val binding by lazy {
        viewBindingInflater(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun showLoading(message: String?) {
        Toast.makeText(requireContext(), "showLoading", Toast.LENGTH_SHORT).show()
    }

    override fun hideLoading() {
        Toast.makeText(requireContext(), "makeText", Toast.LENGTH_SHORT).show()
    }
}