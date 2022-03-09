package com.stash.shopeklobek.ui.home.product_details

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.entities.room.RoomFavorite
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import kotlinx.coroutines.launch

class ProductDetailsViewModel(application: Application) : AndroidViewModel(application) {

    val repo = ProductRepo(ShopifyApi.api, SettingsPreferences.getInstance(application),application)

   suspend fun addToCart(product: Products){
       repo.addToCart(product)
   }
      fun addToFavorite(product: Products){
        repo.addToFavorite(product)
    }




    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProductDetailsViewModel(application) as T
        }
    }
}
