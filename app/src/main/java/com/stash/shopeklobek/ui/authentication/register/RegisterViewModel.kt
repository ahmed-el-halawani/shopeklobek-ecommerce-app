package com.stash.shopeklobek.ui.authentication.register

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.model.entities.CustomerModel
import com.stash.shopeklobek.model.repositories.AuthenticationRepo
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application,val authenticationRepo: AuthenticationRepo) : AndroidViewModel(application) {
    val signupSuccess: MutableLiveData<Boolean?> = MutableLiveData()

    fun postData(customer:CustomerModel){
        viewModelScope.launch {
            val response : Either<CustomerModel, RepoErrors> = authenticationRepo.signUp(customer)

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
                    signupSuccess.postValue(true)
                }
            }
        }
    }



    class Factory(private val application: Application,val authenticationRepo: AuthenticationRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(application,authenticationRepo) as T
        }
    }

    companion object{
        fun create(context: Fragment):RegisterViewModel{
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
            )[RegisterViewModel::class.java]
        }
    }

}