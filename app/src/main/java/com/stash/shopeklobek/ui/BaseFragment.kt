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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.activityPermissionResultData.observe(viewLifecycleOwner){
            if(it!=null)
                onRequestPermissionsResult(it)
        }
        mainActivity.activityResultLiveData.observe(viewLifecycleOwner)
        {
            if(it!=null)
                onActivityResult(it)
        }
    }


    override fun showLoading(message: String?) {
        mainActivity.showLoading(message)
    }

    override fun hideLoading() {
        mainActivity.hideLoading()
    }


    open fun onActivityResult(activityResultData: MainActivity.ActivityResultData) {}

    open fun onRequestPermissionsResult(permissionResultData: MainActivity.ActivityPermissionResultData) {}
}