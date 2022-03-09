package com.stash.shopeklobek.ui.home.brands

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.DiscountModel
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.entities.ProductsModel
import com.stash.shopeklobek.model.entities.SmartCollectionModel
import com.stash.shopeklobek.model.entities.room.RoomFavorite
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.utils.Constants.TAG
import kotlinx.coroutines.launch

class BrandsViewModel(application: Application) : AndroidViewModel(application) {

    val brands = MutableLiveData<Either<SmartCollectionModel, RepoErrors>>()
    var vendors = MutableLiveData<Either<ProductsModel,RepoErrors>>()
    var discounts = MutableLiveData<Either<DiscountModel,RepoErrors>>()
    var favorites = MutableLiveData<List<RoomFavorite>>()

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

    fun getFavorites() {
        Log.i(TAG, "getFavorites: ")
        when(val favorite = repo.getFavorites()){
            is Either.Error -> {
                Log.i(TAG, "getFavorites: error")} 
            is Either.Success -> {favorites.value = favorite.data.value
                Log.d("getFavorites", favorite.data.toString())
                Log.i(TAG, "getFavorites: success")
            }
        }
    }
    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BrandsViewModel(application) as T
        }
    }
}