package com.stash.shopeklobek.ui.home.brands

import android.app.Application
import androidx.lifecycle.*
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.SmartCollectionModel
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import kotlinx.coroutines.launch

class BrandsViewModel(application: Application) : AndroidViewModel(application) {

    val brands = MutableLiveData<Either<SmartCollectionModel, RepoErrors>>()
    val loadingLiveData = MutableLiveData<Boolean>(false)

    private val repo = ProductRepo(ShopifyApi.api, SettingsPreferences(application),application)

    fun getSmartCollection()  {
        viewModelScope.launch {
            brands.value= repo.getSmartCollection()
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BrandsViewModel(application) as T
        }
    }
}