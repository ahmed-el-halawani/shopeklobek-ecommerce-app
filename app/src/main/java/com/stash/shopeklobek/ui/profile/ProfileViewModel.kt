package com.stash.shopeklobek.ui.profile

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.room.RoomFavorite
import com.stash.shopeklobek.model.entities.room.RoomOrder
import com.stash.shopeklobek.model.repositories.ProductRepo

class ProfileViewModel(application: Application,val productRepo: ProductRepo) : AndroidViewModel(application) {



    var favorites = MutableLiveData<List<RoomFavorite>>()
    var orders = MutableLiveData<List<RoomOrder>>()



    fun getOrders()  {
        orders.value=productRepo.getOrders().value

    }

    fun getFavorites() {
        favorites.value=productRepo.getFavorites().value
    }



    class Factory(private val application: Application,val productRepo: ProductRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(application,productRepo) as T
        }
    }

    companion object{
        fun create(context: Fragment):ProfileViewModel = ViewModelProvider(
                context,
                Factory(
                    context.context?.applicationContext as Application,
                    ProductRepo(ShopifyApi.api, SettingsPreferences(context.context?.applicationContext as Application)
                        ,context.context?.applicationContext as Application)
                )
            )[ProfileViewModel::class.java]
    }
}