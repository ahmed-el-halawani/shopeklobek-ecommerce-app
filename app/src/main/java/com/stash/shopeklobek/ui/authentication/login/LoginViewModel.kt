package com.stash.shopeklobek.ui.authentication.login

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.LoginErrors
import com.stash.shopeklobek.model.entities.CustomerLoginModel
import com.stash.shopeklobek.model.repositories.AuthenticationRepo
import kotlinx.coroutines.launch


class LoginViewModel(application: Application,val AuthRepo: AuthenticationRepo) : AndroidViewModel(application) {
    val loginSuccess: MutableLiveData<Boolean?> = MutableLiveData()


    fun getData(email:String,password:String){
        viewModelScope.launch {
            val response : Either<CustomerLoginModel, LoginErrors> = AuthRepo.login(email,password)

            when(response){
                is Either.Error -> when(response.errorCode){
                    LoginErrors.NoInternetConnection -> {
                        Toast.makeText(getApplication(), "NoInternetConnection"+response.message, Toast.LENGTH_SHORT).show()
                        loginSuccess.postValue(false)
                    }
                    LoginErrors.ServerError -> {

                        Toast.makeText(getApplication(), "ServerError"+response.message, Toast.LENGTH_SHORT).show()
                        loginSuccess.postValue(false)
                    }
                    LoginErrors.CustomerNotFound ->{
                        Toast.makeText(getApplication(), "CustomerNotFound"+response.message, Toast.LENGTH_SHORT).show()
                        loginSuccess.postValue(false)
                    }
                }
                is Either.Success -> {
                    Log.d("haa",""+response.data)
                    loginSuccess.postValue(true)
                }
            }
        }
    }


    class Factory(private val application: Application,val AuthRepo: AuthenticationRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(application,AuthRepo) as T
        }
    }
    companion object{
        fun create(context: Fragment): LoginViewModel {
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
            )[LoginViewModel::class.java]
        }
    }
}