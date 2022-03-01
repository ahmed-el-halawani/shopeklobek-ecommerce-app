package com.stash.shopeklobek.ui.authentication.register

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.newcore.wezy.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ApiService
import com.stash.shopeklobek.model.api.Either
import com.stash.shopeklobek.model.api.LoginErrors
import com.stash.shopeklobek.model.api.RepoErrors
import com.stash.shopeklobek.model.entities.CustomerLoginModel
import com.stash.shopeklobek.model.entities.CustomerModel
import com.stash.shopeklobek.model.repositories.AuthenticationRepo
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.ui.profile.ProfileViewModel
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application,val authenticationRepo: AuthenticationRepo) : AndroidViewModel(application) {
    val signupSuccess: MutableLiveData<Boolean?> = MutableLiveData()

    fun postData(customer:CustomerModel){
        viewModelScope.launch {
            val response : Either<CustomerModel, RepoErrors> = authenticationRepo.signUp(customer)
            Log.d("useeeer",""+customer.customer!!.firstName)
            Log.d("useeeer",""+customer.customer!!.email)

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
                    signupSuccess.postValue(true)
                }
            }
        }
    }

    fun postData(){
        viewModelScope.launch {
            val response : Either<CustomerLoginModel, LoginErrors> = authenticationRepo.login("agomgaa528.ag@gmail.com")

            when(response){
                is Either.Error -> when(response.errorCode){
                    LoginErrors.NoInternetConnection -> {
                        Toast.makeText(getApplication(), "NoInternetConnection"+response.message, Toast.LENGTH_SHORT).show()
                    }
                    LoginErrors.ServerError -> {

                        Toast.makeText(getApplication(), "ServerError"+response.message, Toast.LENGTH_SHORT).show()
                    }
                    LoginErrors.CustomerNotFound ->{
                        Toast.makeText(getApplication(), "CustomerNotFound"+response.message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Either.Success -> {
                    Log.d("haa",""+response.data)
//                    signupSuccess.postValue(true)
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
                        ApiService.api,
                        SettingsPreferences(context.context?.applicationContext as Application),
                        context.context?.applicationContext as Application
                    )
                )
            )[RegisterViewModel::class.java]
        }
    }

}