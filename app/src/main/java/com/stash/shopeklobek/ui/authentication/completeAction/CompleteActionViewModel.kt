package com.stash.shopeklobek.ui.authentication.completeAction

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.repositories.AuthenticationRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences

class CompleteActionViewModel(application: Application, val AuthRepo: AuthenticationRepo) :
    AndroidViewModel(application) {
    class Factory(private val application: Application, val AuthRepo: AuthenticationRepo) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CompleteActionViewModel(application, AuthRepo) as T
        }
    }

    companion object {
        fun create(context: Fragment): CompleteActionViewModel {
            return ViewModelProvider(
                context,
                Factory(
                    context.context?.applicationContext as Application,
                    AuthenticationRepo(
                        ShopifyApi.api,
                        SettingsPreferences.getInstance(context.context?.applicationContext as Application),
                        context.context?.applicationContext as Application
                    )
                )
            )[CompleteActionViewModel::class.java]
        }
    }

}