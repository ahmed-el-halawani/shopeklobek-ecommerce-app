package com.stash.shopeklobek.ui.home.cart

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.room.RoomCart
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import kotlinx.coroutines.launch

class CartViewModel(application: Application, val productRepo: ProductRepo) : AndroidViewModel(application) {


    val settingsLiveData by lazy {
        productRepo.getSettingsLiveData()
    }

    val cartLiveData = MutableLiveData<List<RoomCart>>()

    fun getCartProducts() {
        when (val productCartRes = productRepo.getCart()) {
            is Either.Error -> {
                cartLiveData.postValue(emptyList())
                Log.e("getCartProducts", "getCartProducts: Errorroror", )
            }
            is Either.Success -> {
                Transformations.map(
                    productCartRes.data,
                    cartLiveData::postValue
                )
            }
        }
    }

    fun deleteCartProduct(id: Long) = productRepo.deleteFromCart(id)

    fun addCartProduct(roomCart: RoomCart) = viewModelScope.launch {
        productRepo.addToCart(roomCart.product, roomCart.variantId)
    }

    suspend fun updateCartProduct(roomCart: RoomCart) = productRepo.updateProductCart(roomCart)


    class Factory(private val application: Application, val productRepo: ProductRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CartViewModel(application, productRepo) as T
        }
    }

    companion object {
        fun create(context: Fragment): CartViewModel {
            val application = context.context?.applicationContext as Application
            return ViewModelProvider(
                context,
                Factory(
                    application,
                    ProductRepo(
                        ShopifyApi.api,
                        SettingsPreferences(application),
                        application
                    )
                )
            )[CartViewModel::class.java]
        }
    }
}