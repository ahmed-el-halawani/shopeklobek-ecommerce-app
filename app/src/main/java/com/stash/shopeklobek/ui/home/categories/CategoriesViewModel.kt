package com.stash.shopeklobek.ui.home.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.newcore.wezy.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ApiService
import com.stash.shopeklobek.model.api.Either
import com.stash.shopeklobek.model.api.RepoErrors
import com.stash.shopeklobek.model.entities.MainCategories
import com.stash.shopeklobek.model.entities.ProductsModel
import com.stash.shopeklobek.model.entities.SmartCollectionModel
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.utils.Constants.TAG
import kotlinx.coroutines.launch

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {

    val category = MutableLiveData<Either<MainCategories, RepoErrors>>()

    val products = MutableLiveData<Either<ProductsModel,RepoErrors>>()

    val repo = ProductRepo(ApiService.api, SettingsPreferences(application),application)

    fun getMainCategory() {
        viewModelScope.launch {
            category.value= repo.getMainCategories()
        }
    }

    fun getAllCategory(){
        viewModelScope.launch {
            products.value = repo.getAllProduct()
        }
    }

    fun getProductsByGender(collectionId : Long){
        viewModelScope.launch {
            products.value = repo.getProductsByGender(collectionId)
        }
    }

    fun getProductsFromType (collectionId : Long, productType: String){
        viewModelScope.launch {
            products.value = repo.getProductsFromType(collectionId,productType)
        }
    }


    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CategoriesViewModel(application) as T
        }
    }
}