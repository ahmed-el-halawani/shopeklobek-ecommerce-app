package com.stash.shopeklobek.ui.home.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.MainCategories
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.entities.ProductsModel
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.utils.Constants.TAG
import kotlinx.coroutines.launch

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {

    val category = MutableLiveData<Either<MainCategories, RepoErrors>>()
    var products = MutableLiveData<Either<ProductsModel,RepoErrors>>()
    var firstFilter = MutableLiveData<String>()
    var secondFilter = MutableLiveData<String>()

    private val repo = ProductRepo(ShopifyApi.api, SettingsPreferences.getInstance(application),application)

    init {
        getMainCategory()
    }

    private fun getMainCategory() {
        viewModelScope.launch {
            category.value= repo.getMainCategories()
        }
    }

    fun getAllCategory(){
        viewModelScope.launch {
            products.value = repo.getAllProduct()
        }
    }

    fun getProductsByGender(collectionId : Long, gender : String){
        viewModelScope.launch {
            products.value = repo.getProductsByGender(collectionId)
            firstFilter.value = gender
            secondFilter.value="all"
        }
    }

    fun getProductsFromType (collectionId : Long, productType: String,gender : String){
        viewModelScope.launch {
            products.value = repo.getProductsFromType(collectionId,productType)
            firstFilter.value = gender
            secondFilter.value = productType
            }
    }

    fun addToFavorite(product: Products){
        Log.i(TAG, "addToFavorite: from viewmodel")
        repo.addToFavorite(product)
    }


    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CategoriesViewModel(application) as T
        }
    }
}