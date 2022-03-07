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
    IncorrectEmailOrPassword
}

enum class RoomAddProductErrors{
    ProductAlreadyExist,
    ProductIdNotFound,
    RoomError,
    NoLoginCustomer,
}

enum class RoomUpdateProductError{
    RoomError,
}

enum class RoomCustomerError{
    NoLoginCustomer,
}