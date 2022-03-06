package com.stash.shopeklobek.ui.settings

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.repositories.AuthenticationRepo
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.ui.authentication.login.LoginViewModel

class SettingsViewModel(application: Application,val productRepo: ProductRepo) : AndroidViewModel(application) {





    class Factory(private val application: Application,val productRepo: ProductRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingsViewModel(application,productRepo) as T
        }
    }
    companion object{
        fun create(context: Fragment): SettingsViewModel {
            return ViewModelProvider(
                context,
                Factory(
                    context.context?.applicationContext as Application,
                    ProductRepo(
                        ShopifyApi.api,
                        SettingsPreferences(context.context?.applicationContext as Application),
                        context.context?.applicationContext as Application
                    )
                )
            )[SettingsViewModel::class.java]
        }
    }
}