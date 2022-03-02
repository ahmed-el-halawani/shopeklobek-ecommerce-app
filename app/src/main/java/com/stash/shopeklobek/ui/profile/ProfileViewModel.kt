package com.stash.shopeklobek.ui.profile

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.newcore.wezy.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ApiService
import com.stash.shopeklobek.model.repositories.ProductRepo

class ProfileViewModel(application: Application,val productRepo: ProductRepo) : AndroidViewModel(application) {





    class Factory(private val application: Application,val productRepo: ProductRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(application,productRepo) as T
        }
    }

    companion object{
        fun create(context: Fragment):ProfileViewModel = ViewModelProvider(
                context,
                Factory(
                    context.context?.applicationContext as Application,
                    ProductRepo(ApiService.api, SettingsPreferences(context.context?.applicationContext as Application)
                        ,context.context?.applicationContext as Application)
                )
            )[ProfileViewModel::class.java]
    }
}