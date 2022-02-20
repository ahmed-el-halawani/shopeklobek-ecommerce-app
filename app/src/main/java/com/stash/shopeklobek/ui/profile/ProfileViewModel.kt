package com.stash.shopeklobek.ui.profile

import android.app.Application
import androidx.lifecycle.*

class ProfileViewModel(application: Application) : AndroidViewModel(application) {





    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(application) as T
        }
    }
}