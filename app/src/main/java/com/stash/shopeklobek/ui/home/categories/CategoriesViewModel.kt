package com.stash.shopeklobek.ui.home.categories

import android.app.Application
import androidx.lifecycle.*

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {





    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CategoriesViewModel(application) as T
        }
    }
}