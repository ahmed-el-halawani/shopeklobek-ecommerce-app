package com.stash.shopeklobek.ui.home.categories

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.MainCategories
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.entities.ProductsModel
import com.stash.shopeklobek.model.entities.room.RoomFavorite
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.model.utils.RoomAddProductErrors
import com.stash.shopeklobek.utils.Constants.TAG
import kotlinx.coroutines.launch

class CategoriesViewModel(application: Application) : AndroidViewModel(application) {

    val category = MutableLiveData<Either<MainCategories, RepoErrors>>()
    var products = MutableLiveData<Either<ProductsModel,RepoErrors>>()
    var hashmap = HashMap<String,Long>()

    var firstFilter = MutableLiveData<String>()
    var secondFilter = MutableLiveData<String>()
    var firstPriceFilter = MutableLiveData<Float>()
    var secondPriceFilter = MutableLiveData<Float>()

    val loadingLiveData = MutableLiveData<Boolean>(false)


    private val repo = ProductRepo(ShopifyApi.api, SettingsPreferences.getInstance(application),application)

    init {
        getMainCategory()
        getAllCategory()
        firstPriceFilter.value=0f
        secondPriceFilter.value=1000f
    }

    private fun getMainCategory() {
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            category.value= repo.getMainCategories()
            loadingLiveData.postValue(false)
        }
    }

    private fun getAllCategory(){
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            products.value = repo.getAllProduct()
            loadingLiveData.postValue(false)
        }
    }

    fun getProductsByGender(collectionId : Long, gender : String){
        viewModelScope.launch {
            loadingLiveData.postValue(true)
            products.value = repo.getProductsByGender(collectionId)
            firstFilter.value = gender
            secondFilter.value="all"
            loadingLiveData.postValue(false)
        }
    }

    fun getProductsFromType (collectionId : Long, productType: String,gender : String){
        viewModelScope.launch {
            products.value = repo.getProductsByGender(collectionId)
            products.value = repo.getProductsFromType(collectionId,productType)
            firstFilter.value = gender
            secondFilter.value = productType
            loadingLiveData.postValue(false)
        }
    }

    fun addToFavorite(product: Products) : Either<Unit, RoomAddProductErrors>{
        return  repo.addToFavorite(product)
    }

    fun getFavorites() = repo.getFavorites()

    fun deleteFavorite(product: Products) {
        viewModelScope.launch {
            repo.deleteFromFavorite(product)
        }
    }


    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CategoriesViewModel(application) as T
        }
    }
}