package com.stash.shopeklobek.ui.checkout

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.paypal.pyplcheckout.utils.getOrAwaitValue
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.room.RoomCart
import com.stash.shopeklobek.model.repositories.ProductRepo
import kotlinx.coroutines.launch

class CheckoutViewModel(application: Application, val productRepo: ProductRepo) : AndroidViewModel(application) {

    val loadingLiveData = MutableLiveData(false)

    val pageIndexLiveData = MutableLiveData(0)

    val products:LiveData<List<RoomCart>> by lazy{
        productRepo.getCart()
    }

    private suspend fun <T> loadingBloc(func:suspend ()->T):T{
        var res:T? = null
        loadingLiveData.value = true
        res = func()
        loadingLiveData.value = false
        return res
    }

    class Factory(private val application: Application,val productRepo: ProductRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CheckoutViewModel(application,productRepo) as T
        }
    }

    companion object{
        fun create(context: CheckoutActivity):CheckoutViewModel{
            return ViewModelProvider(
                context,
                Factory(
                    context.applicationContext as Application,
                    ProductRepo(ShopifyApi.api, SettingsPreferences.getInstance(context.applicationContext as Application)
                        ,context.applicationContext as Application)
                )
            )[CheckoutViewModel::class.java]
        }
    }

}