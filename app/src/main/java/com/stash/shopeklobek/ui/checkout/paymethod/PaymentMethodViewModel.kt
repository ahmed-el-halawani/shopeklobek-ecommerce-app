package com.stash.shopeklobek.ui.checkout.paymethod

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ApiService
import com.stash.shopeklobek.model.repositories.ProductRepo

class PaymentMethodViewModel(application: Application, val productRepo: ProductRepo) : AndroidViewModel(application) {

    class Factory(private val application: Application,val productRepo: ProductRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PaymentMethodViewModel(application,productRepo) as T
        }
    }

    companion object{
        fun create(context: Fragment):PaymentMethodViewModel{
            return ViewModelProvider(
                context,
                Factory(
                    context.context?.applicationContext as Application,
                    ProductRepo(ApiService.api, SettingsPreferences(context.context?.applicationContext as Application)
                        ,context.context?.applicationContext as Application)
                )
            )[PaymentMethodViewModel::class.java]
        }
    }
}