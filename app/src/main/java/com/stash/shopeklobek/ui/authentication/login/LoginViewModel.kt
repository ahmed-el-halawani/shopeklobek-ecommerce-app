package com.stash.shopeklobek.ui.authentication.login

import android.app.Application
import androidx.lifecycle.*

class LoginViewModel(application: Application) : AndroidViewModel(application) {





    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(application) as T
        }
    }
}