package com.stash.shopeklobek.model.repositories

import android.app.Application
import android.util.Log
import com.newcore.wezy.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ApiService.api
import com.stash.shopeklobek.model.api.Either
import com.stash.shopeklobek.model.api.RepoErrors
import com.stash.shopeklobek.model.entities.*
import com.stash.shopeklobek.model.interfaces.ShopifyServices
import com.stash.shopeklobek.utils.NetworkingHelper.hasInternet
import retrofit2.Response

class ProductRepo (
    val shopifyServices: ShopifyServices,
    val settingsPreferences: SettingsPreferences,
    val application:Application
) {

    suspend fun getSmartCollection(): Either<SmartCollectionModel,RepoErrors>{
        return try {
            return  if(hasInternet(application.applicationContext)){
                val res = shopifyServices.getSmartCollection()
                if(res.isSuccessful){
                    Either.Success(res.body()!!)
                }else{
                    Either.Error(RepoErrors.ServerError, res.message())
                }
            }else{
                Either.Error(RepoErrors.NoInternetConnection)
            }
        }catch (t:Throwable){
            Either.Error(RepoErrors.ServerError,t.message)
        }
    }

    suspend fun getProductsByVendor(vendor: String): Either<Nothing,RepoErrors>{
        TODO("Not yet implemented")
    }

     suspend fun getMainCategories(): Either<MainCategories, RepoErrors> {
         return try {
             return  if(hasInternet(application.applicationContext)){
                 val res = shopifyServices.getMainCategories()
                 if(res.isSuccessful) {
                     Either.Success(res.body()!!)
                 }else {
                     Either.Error(RepoErrors.ServerError, res.message())
                 }
             }else{
                 Either.Error(RepoErrors.NoInternetConnection)
             }
         }catch (t:Throwable){
             Either.Error(RepoErrors.ServerError,t.message)
         }
     }

    suspend fun getAllProduct() : Either<ProductsModel,RepoErrors>{
        return try {
            return  if(hasInternet(application.applicationContext)){
                val res = shopifyServices.getAllProducts()
                if(res.isSuccessful) {
                    Either.Success(res.body()!!)
                }else {
                    Either.Error(RepoErrors.ServerError, res.message())
                }
            }else{
                Either.Error(RepoErrors.NoInternetConnection)
            }
        }catch (t:Throwable){
            Either.Error(RepoErrors.ServerError,t.message)
        }
    }

     suspend fun getProductsByGender(collectionId: Long): Either<ProductsModel,RepoErrors>{
         return try {
             return  if(hasInternet(application.applicationContext)){
                 val res = shopifyServices.getProductsByGender(collectionId)
                 if(res.isSuccessful) {
                     Either.Success(res.body()!!)
                 }else {
                     Either.Error(RepoErrors.ServerError, res.message())
                 }
             }else{
                 Either.Error(RepoErrors.NoInternetConnection)
             }
         }catch (t:Throwable){
             Either.Error(RepoErrors.ServerError,t.message)
         }
    }

     suspend fun getProductsFromType(collectionId: Long,productType: String): Either<ProductsModel,RepoErrors>{
         return try {
             return  if(hasInternet(application.applicationContext)){
                 val res = shopifyServices.getProductsFromType(collectionId,productType)
                 if(res.isSuccessful) {
                     Either.Success(res.body()!!)
                 }else {
                     Either.Error(RepoErrors.ServerError, res.message())
                 }
             }else{
                 Either.Error(RepoErrors.NoInternetConnection)
             }
         }catch (t:Throwable){
             Either.Error(RepoErrors.ServerError,t.message)
         }
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