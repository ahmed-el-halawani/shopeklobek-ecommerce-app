package com.stash.shopeklobek.model.repositories

import android.app.Application
import com.newcore.wezy.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ApiService.api
import com.stash.shopeklobek.model.api.Either
import com.stash.shopeklobek.model.api.RepoErrors
import com.stash.shopeklobek.model.entities.*
import com.stash.shopeklobek.model.interfaces.ShopifyServices
import com.stash.shopeklobek.utils.NetworkingHelper.hasInternet
import retrofit2.Response

class ProductRepo (
    val ShopifyServices: ShopifyServices,
    val settingsPreferences: SettingsPreferences,
    val application:Application
) {

     suspend fun getMainCategories(): Either<MainCategories, RepoErrors> {
         return try {
             return  if(hasInternet(application.applicationContext)){
                 val res = ShopifyServices.getMainCategories()
                 if(res.isSuccessful)
                     Either.Success(res.body()!!)
                 else
                     Either.Error(RepoErrors.ServerError,res.message())

             }else{
                 Either.Error(RepoErrors.NoInternetConnection)
             }
         }catch (t:Throwable){
             Either.Error(RepoErrors.ServerError,t.message)
         }
     }

     suspend fun getProducts(collectionId: Long): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun getProductsByVendor(vendor: String): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun getProductsFromType(productType: String): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun createDiscount(priceRule: Discount): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun getDiscount(discountId: Long): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun getProductImage(ProductId: Long): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun smartCollection(): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun updateCustomer(customerId: Long, customer: EditCustomerModel): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun addOrder(order: AddOrderModel): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun getOrders(email: String): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun addAddress(customerId: Long, address: AddressModel): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun deleteAddress(customerId: Long, addressId: Long): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun updateAddress(customerId: Long, addressId: Long, address: AddressModel): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun getAddress(customerId: Long): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

}