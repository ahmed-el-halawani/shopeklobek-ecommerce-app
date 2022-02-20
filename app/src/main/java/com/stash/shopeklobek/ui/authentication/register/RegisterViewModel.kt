package com.stash.shopeklobek.ui.authentication.register

import android.app.Application
import androidx.lifecycle.*

class RegisterViewModel(application: Application) : AndroidViewModel(application) {





    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(application) as T
        }
    }
}