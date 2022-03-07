package com.stash.shopeklobek.ui.home.brands

import android.app.Application
import androidx.lifecycle.*
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.DiscountModel
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.entities.ProductsModel
import com.stash.shopeklobek.model.entities.SmartCollectionModel
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import kotlinx.coroutines.launch

class BrandsViewModel(application: Application) : AndroidViewModel(application) {

    val brands = MutableLiveData<Either<SmartCollectionModel, RepoErrors>>()
    var vendors = MutableLiveData<Either<ProductsModel,RepoErrors>>()
    var discounts = MutableLiveData<Either<DiscountModel,RepoErrors>>()
    val loadingLiveData = MutableLiveData<Boolean>(false)

    private val repo = ProductRepo(ShopifyApi.api, SettingsPreferences.getInstance(application),application)

    init {
        getAllDiscounts()
    }

    fun getSmartCollection()  {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            brands.value = repo.getSmartCollection()
            loadingLiveData.postValue(false)
        }
    }

    fun getProductsByVendor(vendor : String){
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            vendors.value = repo.getProductsByVendor(vendor)
        }
    }

    fun addToFavorite(product: Products){
        repo.addToFavorite(product)
    }

    fun getAllDiscounts(){
        viewModelScope.launch {
            discounts.value = repo.getAllDiscounts()
        }
    }
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BrandsViewModel(application) as T
        }
    }
}