package com.stash.shopeklobek.ui.settings

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.repositories.AuthenticationRepo
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.LoginErrors
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.ui.authentication.login.LoginViewModel
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application,val productRepo: ProductRepo) : AndroidViewModel(application) {
    val currencySuccess: MutableLiveData<Boolean?> = MutableLiveData()

fun getCurrency(currency:String){

    viewModelScope.launch {
        val response:Either<Unit,RepoErrors> = productRepo.selectCurrency(currency)
        when(response){
            is Either.Error -> when(response.errorCode){
                RepoErrors.NoInternetConnection -> {
                    Toast.makeText(getApplication(), "NoInternetConnection"+response.message, Toast.LENGTH_SHORT).show()
                }
                RepoErrors.ServerError -> {

                    Toast.makeText(getApplication(), "ServerError"+response.message, Toast.LENGTH_SHORT).show()
                }
            }
            is Either.Success -> {
                Log.d("haa",""+response.data)
                currencySuccess.postValue(true)
            }
        }
    }
}



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