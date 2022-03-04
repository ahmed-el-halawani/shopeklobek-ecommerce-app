package com.stash.shopeklobek.ui.home.brands

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.newcore.wezy.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ApiService
import com.stash.shopeklobek.model.api.Either
import com.stash.shopeklobek.model.api.RepoErrors
import com.stash.shopeklobek.model.entities.MainCategories
import com.stash.shopeklobek.model.entities.ProductsModel
import com.stash.shopeklobek.model.entities.SmartCollection
import com.stash.shopeklobek.model.entities.SmartCollectionModel
import com.stash.shopeklobek.model.interfaces.ShopifyServices
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.utils.Constants.TAG
import kotlinx.coroutines.launch
import java.util.*

class BrandsViewModel(application: Application) : AndroidViewModel(application) {

    val brands = MutableLiveData<Either<SmartCollectionModel,RepoErrors>>()
    val loadingLiveData = MutableLiveData<Boolean>(false)

    private val repo = ProductRepo(ApiService.api, SettingsPreferences(application),application)

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