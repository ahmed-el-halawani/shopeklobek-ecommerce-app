package com.stash.shopeklobek.model.api

sealed class Either<S,E> {

    data class Success<S>(val data:S) : Either<S, Nothing>()
    data class Error<E>(val errorCode:E,val message:String?=null) : Either<Nothing, E>()

}