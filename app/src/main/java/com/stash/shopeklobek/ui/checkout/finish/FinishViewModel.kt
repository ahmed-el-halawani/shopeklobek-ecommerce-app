package com.stash.shopeklobek.ui.checkout.finish

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.PurchaseUnit
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.utils.getPrice
import com.stash.shopeklobek.utils.toCurrency

class FinishViewModel(application: Application, val productRepo: ProductRepo) : AndroidViewModel(application) {
    val loading = MutableLiveData<Boolean>()

    class Factory(private val application: Application, val productRepo: ProductRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FinishViewModel(application, productRepo) as T
        }
    }

    companion object {
        fun create(context: Fragment): FinishViewModel {
            val application = context.context?.applicationContext as Application
            return ViewModelProvider(
                context,
                Factory(
                    application,
                    ProductRepo(
                        ShopifyApi.api, SettingsPreferences.getInstance(application), application
                    )
                )
            )[FinishViewModel::class.java]
        }
    }
}