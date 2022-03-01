package com.stash.shopeklobek.ui.authentication.register

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.stash.shopeklobek.model.entities.CustomerModel
import com.stash.shopeklobek.model.repositories.AuthenticationRepo
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    val signupSuccess: MutableLiveData<Boolean?> = MutableLiveData()
val authRepo=AuthenticationRepo(application)




    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(application) as T
        }
    }
    fun postData(customer:CustomerModel){
        viewModelScope.launch {
            val response= authRepo.signUp(customer)
            Log.d("useeeer",""+customer.customer!!.firstName)
            Log.d("useeeer",""+customer.customer!!.email)
            if (response.isSuccessful){
                Log.d("haa",""+response.body()!!.customer!!.firstName)
                signupSuccess.postValue(true)
            }
            else{
                Toast.makeText(getApplication(),""+response.message(),Toast.LENGTH_LONG).show()
                signupSuccess.postValue(false)
            }


        }
    }

}