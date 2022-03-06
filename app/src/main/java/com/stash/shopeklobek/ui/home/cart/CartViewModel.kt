package com.stash.shopeklobek.ui.home.cart

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.room.RoomCart
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.ui.MainActivity
import com.stash.shopeklobek.ui.MainViewModel
import kotlinx.coroutines.launch

class CartViewModel(application: Application,val productRepo: ProductRepo) : AndroidViewModel(application) {


    fun getCartProducts(): LiveData<List<RoomCart>> = productRepo.getCart()

    fun deleteCartProduct(id:Long) = productRepo.deleteFromCart(id)

    fun addCartProduct(roomCart: RoomCart)= viewModelScope.launch {
        productRepo.addToCart(roomCart.product,roomCart.variantId)
    }

    suspend fun updateCartProduct(roomCart: RoomCart) = productRepo.updateProductCart(roomCart)


    class Factory(private val application: Application,val productRepo: ProductRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CartViewModel(application,productRepo) as T
        }
    }

    companion object{
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