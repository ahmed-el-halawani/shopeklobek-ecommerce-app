package com.stash.shopeklobek.ui.authentication.login

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.newcore.wezy.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ApiService
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.ui.splash.SplashViewModel

class LoginViewModel(application: Application,val productRepo: ProductRepo) : AndroidViewModel(application) {





    class Factory(private val application: Application,val productRepo: ProductRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(application,productRepo) as T
        }
    }

    companion object{
        fun create(context: Fragment){
            ViewModelProvider(
                context,
                Factory(
                    context.context?.applicationContext as Application,
                    ProductRepo(ApiService.api, SettingsPreferences(context.context?.applicationContext as Application)
                        ,context.context?.applicationContext as Application)
                )
            )[LoginViewModel::class.java]
        }
    }
}