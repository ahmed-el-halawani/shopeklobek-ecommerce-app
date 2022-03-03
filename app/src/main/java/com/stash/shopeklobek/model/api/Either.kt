package com.stash.shopeklobek.model.api

import android.app.Application
import com.stash.shopeklobek.model.entities.currencies.CurrencyConverterResult
import com.stash.shopeklobek.model.shareprefrances.CurrenciesEnum
import com.stash.shopeklobek.utils.CurrencyUtil
import com.stash.shopeklobek.utils.NetworkingHelper
import retrofit2.Response

sealed class Either<S,E> {

    data class Success<S,E>(val data:S) : Either<S, E>()
    data class Error<S,E>(val errorCode:E,val message:String?=null) : Either<S, E>()

}
