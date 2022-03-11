package com.stash.shopeklobek.ui.search

import android.app.Application
import androidx.lifecycle.*
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.entities.ProductsModel
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    var products = MutableLiveData<Either<ProductsModel, RepoErrors>>()

    private val repo = ProductRepo(ShopifyApi.api, SettingsPreferences.getInstance(application),application)

    init {
        getAllCategory()
    }

    private fun getAllCategory(){
        viewModelScope.launch {
            products.value = repo.getAllProduct()
        }
    }

    fun addToFavorite(product: Products){
        repo.addToFavorite(product)
    }

    fun getFavorites() = repo.getFavorites()

    fun deleteFavorite(product: Products) {
        viewModelScope.launch {
            repo.deleteFromFavorite(product)
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(application) as T
        }
    }
}