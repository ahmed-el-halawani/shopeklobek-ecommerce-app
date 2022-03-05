package com.stash.shopeklobek.ui.home.favorites

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.Products
import com.stash.shopeklobek.model.entities.ProductsModel
import com.stash.shopeklobek.model.entities.room.RoomFavorite
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {


    var favorites = MutableLiveData<List<RoomFavorite>>()
    private val repo = ProductRepo(ShopifyApi.api, SettingsPreferences(application),application)


    fun deleteFavorite(id: Long) {
        viewModelScope.launch {
            repo.deleteFromFavorite(id)
        }
    }

    fun getFavorites() {
       favorites.value=repo.getFavorites().value
        Log.d("getFavorites", repo.getFavorites().value?.get(0).toString())
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavoritesViewModel(application) as T
        }
    }
}