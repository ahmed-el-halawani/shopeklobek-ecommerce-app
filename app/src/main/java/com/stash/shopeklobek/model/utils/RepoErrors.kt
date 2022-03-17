package com.stash.shopeklobek.model.utils

enum class RepoErrors {
    NoInternetConnection,
    ServerError,
    EmptyBody,
    NullValue,
    NoLoginCustomer,

}

enum class SignUpErrors {
    NoInternetConnection,
    ServerError,
    EmptyBody,
    NullValue,
    NoLoginCustomer,
    EmailAlreadyExist
}


enum class DiscountError{
    NoInternetConnection,
    ServerError,
    EmptyBody,
    DiscountNotFound
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

enum class RoomAddOrderErrors{
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