package com.stash.shopeklobek.ui.settings

import android.app.Application
import androidx.lifecycle.*

class SettingsViewModel(application: Application) : AndroidViewModel(application) {





    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SettingsViewModel(application) as T
        }
    }
}