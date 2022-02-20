package com.stash.shopeklobek.ui.home.favorites

import android.app.Application
import androidx.lifecycle.*

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {





    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoritesViewModel(application) as T
        }
    }
}