package com.stash.shopeklobek.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.stash.shopeklobek.utils.ILoading

abstract class CheckoutBaseFragment<T : ViewBinding>(val viewBindingInflater:(LayoutInflater)->T): Fragment(),
    ILoading {


    val mainActivity by lazy{
        this.activity as CheckoutActivity
    }

    val binding by lazy {
        viewBindingInflater(layoutInflater)
    }

    val mainViewModel by lazy{
        mainActivity.checkoutVM
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




    open fun onActivityResult(activityResultData: CheckoutActivity.ActivityResultData) {}

    open fun onRequestPermissionsResult(permissionResultData: CheckoutActivity.ActivityPermissionResultData) {}
}