package com.stash.shopeklobek.ui.checkout.search_places

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.stash.shopeklobek.model.api.PlacesApi
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.places.PlacesResult
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchPlacesViewModel(application: Application, val productRepo: ProductRepo) :
    AndroidViewModel(application) {

    val loading = MutableLiveData(false)

    val addressList = MutableLiveData<PlacesResult>()


    var job: Job? = null
    fun findAddress(query: String) {
        loading.value = true
        try {
            job?.cancel()
            job = null
            job = viewModelScope.launch {

                delay(500)
                val res = PlacesApi.findPlaceApi.getAutoCompletePlaces(query)
                if (res.isSuccessful) {
                    addressList.postValue(res.body())
                }
                loading.value = false
            }

        } catch (t: Throwable) {
            Log.e("PrepareListener", "prepareListener: " + t.message)
        }
    }

    class Factory(private val application: Application, val productRepo: ProductRepo) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchPlacesViewModel(application, productRepo) as T
        }
    }

    companion object {
        fun create(context: Fragment): SearchPlacesViewModel {
            val application = context.context?.applicationContext as Application
            return ViewModelProvider(
                context,
                Factory(
                    application,
                    ProductRepo(
                        ShopifyApi.api, SettingsPreferences.getInstance(application), application
                    )
                )
            )[SearchPlacesViewModel::class.java]
        }
    }
}