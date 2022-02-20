package com.stash.shopeklobek.ui.home.cart

import android.app.Application
import androidx.lifecycle.*

class CartViewModel(application: Application) : AndroidViewModel(application) {





    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CartViewModel(application) as T
        }
    }
}