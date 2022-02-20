package com.stash.shopeklobek.ui.home

import android.app.Application
import androidx.lifecycle.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {





    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(application) as T
        }
    }
}