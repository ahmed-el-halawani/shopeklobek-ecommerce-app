package com.stash.shopeklobek.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import com.stash.shopeklobek.utils.ILoading

abstract class CheckoutBaseFragment<T : ViewBinding>(val viewBindingInflater:(LayoutInflater)->T): Fragment(),
    ILoading {


    val checkoutActivity by lazy{
        this.activity as CheckoutActivity
    }

    val binding by lazy {
        viewBindingInflater(layoutInflater)
    }

    val mainViewModel by lazy{
        checkoutActivity.checkoutVM
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkoutActivity.activityPermissionResultData.observe(viewLifecycleOwner){
            if(it!=null)
                onRequestPermissionsResult(it)
        }
        checkoutActivity.activityResultLiveData.observe(viewLifecycleOwner)
        {
            if(it!=null)
                onActivityResult(it)
        }
    }


    override fun showLoading(message: String?) {
        checkoutActivity.showLoading(message)
    }

    override fun hideLoading() {
        checkoutActivity.hideLoading()
    }




    open fun onActivityResult(activityResultData: CheckoutActivity.ActivityResultData) {}

    open fun onRequestPermissionsResult(permissionResultData: CheckoutActivity.ActivityPermissionResultData) {}
}