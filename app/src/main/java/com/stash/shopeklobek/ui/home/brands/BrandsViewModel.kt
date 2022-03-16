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
import com.stash.shopeklobek.model.utils.RoomAddProductErrors
import com.stash.shopeklobek.utils.Constants.TAG
import kotlinx.coroutines.launch

class BrandsViewModel(application: Application) : AndroidViewModel(application) {

    val brands = MutableLiveData<Either<SmartCollectionModel, RepoErrors>>()
    var vendors = MutableLiveData<Either<ProductsModel,RepoErrors>>()
    var discounts = MutableLiveData<Either<DiscountModel,RepoErrors>>()
    var firstPriceFilter = MutableLiveData<Float>()
    var secondPriceFilter = MutableLiveData<Float>()
    var searching : MutableLiveData<String> = MutableLiveData()


    val loadingLiveData = MutableLiveData<Boolean>(false)

    private val repo = ProductRepo(ShopifyApi.api, SettingsPreferences.getInstance(application),application)

    init {
        getAllDiscounts()
        getSmartCollection()
        firstPriceFilter.value=0f
        secondPriceFilter.value=1000f
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
            loadingLiveData.postValue(false)
        }
    }

    fun addToFavorite(product: Products) : Either<Unit, RoomAddProductErrors>{
        return  repo.addToFavorite(product)
    }

    fun getAllDiscounts(){
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            discounts.value = repo.getAllDiscounts()
            loadingLiveData.postValue(false)

        }
    }

    fun getFavorites() = repo.getFavorites()

    fun deleteFavorite(product: Products) {
        viewModelScope.launch {
            repo.deleteFromFavorite(product)

        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BrandsViewModel(application) as T
        }
    }
}