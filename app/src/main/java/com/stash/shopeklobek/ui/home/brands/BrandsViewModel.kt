package com.stash.shopeklobek.ui.home.brands

import android.app.Application
import androidx.lifecycle.*

class BrandsViewModel(application: Application) : AndroidViewModel(application) {





    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BrandsViewModel(application) as T
        }
    }
}