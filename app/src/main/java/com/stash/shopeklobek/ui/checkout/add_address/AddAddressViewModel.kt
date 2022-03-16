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
import com.stash.shopeklobek.model.entities.places.PlacesResultItem
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences


class AddAddressViewModel(application: Application, val productRepo: ProductRepo) :
    AndroidViewModel(application) {

    var addressSource = Address()
    var placesResult: PlacesResultItem? = null
    val addressLiveData = MutableLiveData(addressSource)

    val searchedAddress = MutableLiveData(placesResult)


    var isDefault = false

    var isValid = false

    var setErrors = false


    fun setSearchedAddress(placesResultItem: PlacesResultItem?) {
        placesResult = placesResultItem
        searchedAddress.postValue(placesResult)
        if (placesResultItem != null) {
            addressSource.copy(
                city = placesResultItem.name,
                country = placesResultItem.country?.name,
                address1 = placesResultItem.generateAddress(),
                latitude = placesResultItem.coordinates?.latitude.toString(),
                longitude = placesResultItem.coordinates?.longitude.toString(),
            )
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

        fun create(context: Fragment): AddAddressViewModel {
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
    }
}