package com.stash.shopeklobek.ui.splash

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.newcore.wezy.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ApiService
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.ui.profile.ProfileViewModel

class SplashViewModel(application: Application,val productRepo: ProductRepo) : AndroidViewModel(application) {




    class Factory(private val application: Application,val productRepo: ProductRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SplashViewModel(application,productRepo) as T
        }
    }

    companion object{
        fun create(context: Fragment){
            ViewModelProvider(
                context,
                Factory(
                    context.context as Application,
                    ProductRepo(ApiService.api, SettingsPreferences(context.context as Application)
                        ,context.context as Application)
                )
            )[SplashViewModel::class.java]
        }
    }
}