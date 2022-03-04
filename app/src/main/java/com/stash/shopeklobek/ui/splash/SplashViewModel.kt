package com.stash.shopeklobek.ui.splash

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.utils.ViewHelpers
import java.util.*

class SplashViewModel(val app: Application, val productRepo: ProductRepo) : AndroidViewModel(app) {

    suspend fun updateCurrency() {
        productRepo.updateCurrency()
    }

     fun updateLanguage(activity:Activity) {
        ViewHelpers.setAppLocale(
            activity, "ar", app.resources
        )
    }


    class Factory(private val application: Application, val productRepo: ProductRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SplashViewModel(application, productRepo) as T
        }
    }

    companion object {
        fun create(context: LauncherSActivity): SplashViewModel {
            return ViewModelProvider(
                context,
                Factory(
                    context.application,
                    ProductRepo(
                        ShopifyApi.api, SettingsPreferences(context.application), context.application
                    )
                )
            )[SplashViewModel::class.java]
        }
    }
}