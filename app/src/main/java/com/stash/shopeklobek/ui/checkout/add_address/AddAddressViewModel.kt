package com.stash.shopeklobek.ui.checkout.add_address

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.Address
import com.stash.shopeklobek.model.entities.AddressModel
import com.stash.shopeklobek.model.entities.autocomplete_places_2.Feature
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences


class AddAddressViewModel(application: Application, val productRepo: ProductRepo) :
    AndroidViewModel(application) {

    var addressSource = Address()
    var placesResult: Feature? = null
    val addressLiveData = MutableLiveData(addressSource)

    val searchedAddress = MutableLiveData(placesResult)


    var isDefault = false

    var isValid = false

    var setErrors = false


    fun setSearchedAddress(future: Feature?) {
        placesResult = future
        searchedAddress.postValue(placesResult)
        if (future != null) {
            addressSource = addressSource.copy(
                city = future.properties.city,
                country = future.properties.country,
                address1 = future.properties.generateAddress(),
                latitude = future.properties.lat.toString(),
                longitude = future.properties.lon.toString(),
            )
            addressLiveData.value = addressSource
        }
    }


    fun setErrors() {
        setErrors = true
        addressLiveData.value = addressSource
    }

    suspend fun addAddress() = productRepo.addAddress(AddressModel(addressSource), isDefault)


    class Factory(private val application: Application, val productRepo: ProductRepo) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddAddressViewModel(application, productRepo) as T
        }
    }

    companion object {
        var addressViewModelInstance: AddAddressViewModel? = null
        private val Lock = Any()

        fun getInstance(context: Fragment): AddAddressViewModel {
            return addressViewModelInstance ?: synchronized(Lock) {
                addressViewModelInstance ?: ViewModelProvider(
                    context,
                    Factory(
                        context.context?.applicationContext as Application,
                        ProductRepo(
                            ShopifyApi.api,
                            SettingsPreferences.getInstance(context.context?.applicationContext as Application),
                            context.context?.applicationContext as Application
                        )
                    )
                )[AddAddressViewModel::class.java].also {
                    addressViewModelInstance = it
                }
            }
        }

        fun create(context: Fragment): AddAddressViewModel {
            return ViewModelProvider(
                context,
                Factory(
                    context.context?.applicationContext as Application,
                    ProductRepo(
                        ShopifyApi.api,
                        SettingsPreferences.getInstance(context.context?.applicationContext as Application),
                        context.context?.applicationContext as Application
                    )
                )
            )[AddAddressViewModel::class.java].also {
                addressViewModelInstance = it
            }
        }
    }
}