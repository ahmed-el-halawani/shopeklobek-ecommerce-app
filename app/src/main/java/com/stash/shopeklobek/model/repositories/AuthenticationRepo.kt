package com.stash.shopeklobek.model.repositories

import android.app.Application
import com.newcore.wezy.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ApiService.api
import com.stash.shopeklobek.model.api.Either
import com.stash.shopeklobek.model.api.RepoErrors
import com.stash.shopeklobek.model.entities.CustomerModel
import com.stash.shopeklobek.model.interfaces.ShopifyServices
import com.stash.shopeklobek.utils.NetworkingHelper
import retrofit2.Response

class AuthenticationRepo(
//    val ShopifyServices: ShopifyServices,
//    val settingsPreferences: SettingsPreferences,
    val application: Application
) {

//      to update settings u can use this
//        settingsPreferences.update {
//            it.copy(
//
//            )
//        }
//        val z = CustomerModel(
//            Customer(
//                email = "ahmed"
//            )
//        )
    suspend fun signUp(customer: CustomerModel):Response<CustomerModel>{
    val res =api.register(customer)
        return res
    }

//    suspend fun register(customer: CustomerModel): Either<CustomerModel, RepoErrors> {
//        return try {
//            return if (NetworkingHelper.hasInternet(application.applicationContext)) {
//               // val res = ShopifyServices.register(customer)
//                if (res.isSuccessful) {
//
////                    settingsPreferences.update {
////                        it.copy(
////                            customer = res.body()?.customer
////                        )
////                    }
//
//                    Either.Success(res.body()!!)
//                } else
//                    Either.Error(RepoErrors.ServerError)
//            } else
//                Either.Error(RepoErrors.NoInternetConnection)
//
//        } catch (t: Throwable) {
//            Either.Error(RepoErrors.ServerError)
//        }
//    }

    suspend fun login(email: String): Either<CustomerModel, RepoErrors> {
        TODO("Not yet implemented")
    }


}