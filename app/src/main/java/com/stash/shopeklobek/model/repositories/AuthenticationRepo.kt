package com.stash.shopeklobek.model.repositories

import android.app.Application
import com.newcore.wezy.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.api.ApiService.api
import com.stash.shopeklobek.model.api.Either
import com.stash.shopeklobek.model.api.LoginErrors
import com.stash.shopeklobek.model.api.RepoErrors
import com.stash.shopeklobek.model.entities.CustomerLoginModel
import com.stash.shopeklobek.model.entities.CustomerModel
import com.stash.shopeklobek.model.interfaces.ShopifyServices
import com.stash.shopeklobek.utils.NetworkingHelper
import retrofit2.Response

class AuthenticationRepo(
    val ShopifyServices: ShopifyServices,
    val settingsPreferences: SettingsPreferences,
    val application: Application
) {



    suspend fun signUp(customer: CustomerModel): Either<CustomerModel, RepoErrors> {
        return try {
            return if (NetworkingHelper.hasInternet(application.applicationContext)) {
                val res =api.register(customer)
                if (res.isSuccessful) {

                    settingsPreferences.update {
                        it.copy(
                            customer = res.body()?.customer
                        )
                    }

                    Either.Success(res.body()!!)
                } else
                    Either.Error(RepoErrors.ServerError,res.message())
            } else
                Either.Error(RepoErrors.NoInternetConnection,"NoInternetConnection")

        } catch (t: Throwable) {
            Either.Error(RepoErrors.ServerError,t.message)
        }
    }

    suspend fun login(email: String): Either<CustomerLoginModel, LoginErrors> {
        return try {
             if (NetworkingHelper.hasInternet(application.applicationContext)) {
                val res =api.login()
                if (res.isSuccessful) {

                    val customer = res.body()?.customer?.first {
                        it?.email.equals(email)
                    } ?: return Either.Error(LoginErrors.CustomerNotFound,"CustomerNotFound")

                    settingsPreferences.update {
                        it.copy(
                            customer = customer
                        )
                    }

                    return Either.Success(res.body()!!)
                } else
                    return Either.Error(LoginErrors.ServerError,res.message())
            } else
                 return Either.Error(LoginErrors.NoInternetConnection,"NoInternetConnection")

        } catch (t: Throwable) {
            Either.Error(LoginErrors.ServerError,t.message)
        }
    }


}