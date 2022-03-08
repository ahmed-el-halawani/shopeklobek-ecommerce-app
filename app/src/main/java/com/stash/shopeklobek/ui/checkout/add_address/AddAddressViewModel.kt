package com.stash.shopeklobek.ui.checkout.add_address

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.Address
import com.stash.shopeklobek.model.entities.AddressModel
import com.stash.shopeklobek.model.entities.Customer
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.utils.Either
import kotlinx.coroutines.launch


class AddAddressViewModel(application: Application, val productRepo: ProductRepo) : AndroidViewModel(application) {

    var addressSource = Address()
    val addressLiveData = MutableLiveData(addressSource)

    var isDefault = false

    var isValid = false

    var setErrors = false

    fun setErrors(){
        setErrors = true
        addressLiveData.value = addressSource
    }

    suspend fun addAddress() = productRepo.addAddress(AddressModel(addressSource),isDefault)


    class Factory(private val application: Application,val productRepo: ProductRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddAddressViewModel(application,productRepo) as T
        }
    }

    companion object{
        fun create(context: Fragment):AddAddressViewModel{
            return ViewModelProvider(
                context,
                Factory(
                    context.context?.applicationContext as Application,
                    ProductRepo(ShopifyApi.api, SettingsPreferences.getInstance(context.context?.applicationContext as Application)
                        ,context.context?.applicationContext as Application)
                )
            )[AddAddressViewModel::class.java]
        }
    }
}