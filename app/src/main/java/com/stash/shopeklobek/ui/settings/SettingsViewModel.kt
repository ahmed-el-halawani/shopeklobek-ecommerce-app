package com.stash.shopeklobek.ui.settings

import android.app.Application
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.CurrenciesEnum
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application, val productRepo: ProductRepo) :
    AndroidViewModel(application) {


    fun getCurrency(currency: CurrenciesEnum) {

        viewModelScope.launch {
            val response: Either<Unit, RepoErrors> = productRepo.selectCurrency(currency)
            when (response) {
                is Either.Error -> when (response.errorCode) {
                    RepoErrors.NoInternetConnection -> {
                        Toast.makeText(
                            getApplication(),
                            "NoInternetConnection" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Toast.makeText(
                            getApplication(),
                            "ServerError" + response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
                is Either.Success -> {


                }
            }
        }
    }


    class Factory(private val application: Application, val productRepo: ProductRepo) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingsViewModel(application, productRepo) as T
        }
    }

    companion object {
        fun create(context: Fragment): SettingsViewModel {
            return ViewModelProvider(
                context,
                Factory(
                    context.context?.applicationContext as Application,
                    ProductRepo(
                        ShopifyApi.api,
                        SettingsPreferences.getInstance(context.context?.applicationContext as Application),
                        context.context?.applicationContext as Application
                    )
                )
            )[SettingsViewModel::class.java]
        }
    }
}