package com.stash.shopeklobek.ui.authentication.register.completeProfile
import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.repositories.AuthenticationRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences


class CompleteProfileViewModel(
    application: Application,
    val authenticationRepo: AuthenticationRepo
) : AndroidViewModel(application) {


    class Factory(
        private val application: Application,
        val authenticationRepo: AuthenticationRepo
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CompleteProfileViewModel(application, authenticationRepo) as T
        }
    }

    companion object {
        fun create(context: Fragment): CompleteProfileViewModel {
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
            )[CompleteProfileViewModel::class.java]
        }
    }
}