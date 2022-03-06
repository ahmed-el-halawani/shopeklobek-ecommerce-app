package com.stash.shopeklobek.model.utils

enum class RepoErrors {
    NoInternetConnection,
    ServerError,
    EmptyBody
}

enum class LoginErrors {
    NoInternetConnection,
    ServerError,
    CustomerNotFound,
}

enum class RoomAddProductErrors{
    ProductAlreadyExist,
    ProductIdNotFound,
    RoomError,
}

enum class RoomUpdateProductError{
    RoomError,
}