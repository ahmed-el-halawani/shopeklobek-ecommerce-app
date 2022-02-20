package com.stash.shopeklobek.ui.splash

import android.app.Application
import androidx.lifecycle.*

class SplashViewModel(application: Application) : AndroidViewModel(application) {





    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SplashViewModel(application) as T
        }
    }
}