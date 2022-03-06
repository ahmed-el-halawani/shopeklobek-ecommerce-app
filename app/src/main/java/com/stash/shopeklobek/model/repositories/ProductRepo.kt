package com.stash.shopeklobek.model.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stash.shopeklobek.model.api.CurrencyApi.currencyConverterApi
import com.stash.shopeklobek.model.entities.*
import com.stash.shopeklobek.model.entities.room.RoomCart
import com.stash.shopeklobek.model.entities.room.RoomFavorite
import com.stash.shopeklobek.model.entities.room.RoomOrder
import com.stash.shopeklobek.model.interfaces.ShopifyServices
import com.stash.shopeklobek.model.room.ProductDatabase
import com.stash.shopeklobek.model.shareprefrances.CurrenciesEnum
import com.stash.shopeklobek.model.shareprefrances.ISettingsPreferences
import com.stash.shopeklobek.model.shareprefrances.Settings
import com.stash.shopeklobek.model.utils.*
import com.stash.shopeklobek.utils.CurrencyUtil
import com.stash.shopeklobek.utils.NetworkingHelper.hasInternet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductRepo(
    private val shopifyServices: ShopifyServices,
    private val settingsPreferences: ISettingsPreferences,
    private val application: Application,
) {
    private val database = ProductDatabase(application)

    suspend fun getSmartCollection(): Either<SmartCollectionModel,RepoErrors>{
        return callErrorsHandler(application,{shopifyServices.getSmartCollection()},{
            Either.Success(it)
        })
    }

    suspend fun getProductsByVendor(vendor: String): Either<ProductsModel,RepoErrors>{
        return callErrorsHandler(application,{shopifyServices.getProductsByVendor(vendor)},{
            Either.Success(it)
        })

    }

    suspend fun getMainCategories(): Either<MainCategories, RepoErrors> {
        return try {
            return if (hasInternet(application.applicationContext)) {
                val res = shopifyServices.getMainCategories()
                if (res.isSuccessful) {
                    Either.Success(res.body()!!)
                } else {
                    Either.Error(RepoErrors.ServerError, res.message())
                }
            } else {
                Either.Error(RepoErrors.NoInternetConnection)
            }
        } catch (t: Throwable) {
            Either.Error(RepoErrors.ServerError, t.message)
        }
    }

    suspend fun getAllProduct(): Either<ProductsModel, RepoErrors> {
        return try {
            return if (hasInternet(application.applicationContext)) {
                val res = shopifyServices.getAllProducts()
                if (res.isSuccessful) {
                    Either.Success(res.body()!!)
                } else {
                    Either.Error(RepoErrors.ServerError, res.message())
                }
            } else {
                Either.Error(RepoErrors.NoInternetConnection)
            }
        } catch (t: Throwable) {
            Either.Error(RepoErrors.ServerError, t.message)
        }
    }

    suspend fun getProductsByGender(collectionId: Long): Either<ProductsModel, RepoErrors> {
        return try {
            return if (hasInternet(application.applicationContext)) {
                val res = shopifyServices.getProductsByGender(collectionId)
                if (res.isSuccessful) {
                    Either.Success(res.body()!!)
                } else {
                    Either.Error(RepoErrors.ServerError, res.message())
                }
            } else {
                Either.Error(RepoErrors.NoInternetConnection)
            }
        } catch (t: Throwable) {
            Either.Error(RepoErrors.ServerError, t.message)
        }
    }

    suspend fun getProductsFromType(collectionId: Long, productType: String): Either<ProductsModel, RepoErrors> {
        return try {
            return if (hasInternet(application.applicationContext)) {
                val res = shopifyServices.getProductsFromType(collectionId, productType)
                if (res.isSuccessful) {
                    Either.Success(res.body()!!)
                } else {
                    Either.Error(RepoErrors.ServerError, res.message())
                }
            } else {
                Either.Error(RepoErrors.NoInternetConnection)
            }
        } catch (t: Throwable) {
            Either.Error(RepoErrors.ServerError, t.message)
        }
    }

    suspend fun createDiscount(priceRule: Discount): Either<Nothing, RepoErrors> {
        TODO("Not yet implemented")
    }

    suspend fun getDiscount(discountId: Long): Either<Nothing, RepoErrors> {
        TODO("Not yet implemented")
    }

    suspend fun getProductImage(ProductId: Long): Either<Nothing, RepoErrors> {
        TODO("Not yet implemented")
    }

    suspend fun smartCollection(): Either<Nothing, RepoErrors> {
        TODO("Not yet implemented")
    }

    suspend fun updateCustomer(customerId: Long, customer: EditCustomerModel): Either<Nothing, RepoErrors> {
        TODO("Not yet implemented")
    }

    suspend fun addOrder(order: AddOrderModel): Either<Nothing, RepoErrors> {
        TODO("Not yet implemented")
    }

    suspend fun getOrders(email: String): Either<Nothing, RepoErrors> {
        TODO("Not yet implemented")
    }

    suspend fun addAddress(customerId: Long, address: AddressModel): Either<Nothing, RepoErrors> {
        TODO("Not yet implemented")
    }

    suspend fun deleteAddress(customerId: Long, addressId: Long): Either<Nothing, RepoErrors> {
        TODO("Not yet implemented")
    }

    suspend fun updateAddress(customerId: Long, addressId: Long, address: AddressModel): Either<Nothing, RepoErrors> {
        TODO("Not yet implemented")
    }

    suspend fun getAddress(customerId: Long): Either<Nothing, RepoErrors> {
        TODO("Not yet implemented")
    }

    //room repo

    fun getOrders(): LiveData<List<RoomOrder>> {
        CoroutineScope(Dispatchers.IO).launch {
            val customerId = settingsPreferences.getSettings().customer?.email
            if (customerId != null) {
                callErrorsHandler(application, { shopifyServices.getOrders(customerId) }) {
                    for (order in it.order) {
                        if (order != null) {
                            database.orderDao().upsert(
                                RoomOrder(
                                    order.id ?: 0,
                                    order
                                )
                            )
                        }
                    }
                    Either.Success(it)
                }
            }
        }

        return database.orderDao().getAll()
    }


    fun getFavorites(): LiveData<List<RoomFavorite>> {
        return database.favoriteDao().getAll()
    }

    fun getCart(): LiveData<List<RoomCart>> {
        return database.cartDao().getAll()
    }


    suspend fun addToCart(product: Products, variantId: Long? = null): Either<Unit, RoomAddProductErrors> {
        try {
            if (product.productId == null) return Either.Error(RoomAddProductErrors.ProductIdNotFound)
            if (database.cartDao().getWithId(product.productId) != null) return Either.Error(RoomAddProductErrors.ProductAlreadyExist)
            database.cartDao().upsert(
                RoomCart(
                    id = product.productId,
                    product = product,
                    variantId = variantId
                )
            )
            return Either.Success(Unit)
        } catch (t: Throwable) {
            return Either.Error(RoomAddProductErrors.RoomError)
        }
    }

    suspend fun updateProductCart(roomCart: RoomCart): Either<Unit, RoomUpdateProductError> {
        return try {
            database.cartDao().upsert(roomCart)
            Either.Success(Unit)
        } catch (t: Throwable) {
            Either.Error(RoomUpdateProductError.RoomError)
        }
    }

    fun addToFavorite(product: Products) {
        CoroutineScope(Dispatchers.IO).launch {
            database.favoriteDao().upsert(
                RoomFavorite(
                    product.productId ?: 0,
                    product
                )
            )
        }
    }

    fun deleteFromCart(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            database.cartDao().delete(id)
        }
    }

    suspend fun deleteFromFavorite(id: Long) {
        database.favoriteDao().delete(id)
    }


    // settings repo
    fun insert(settings: Settings) = settingsPreferences.insert(settings)

    fun update(update: (Settings) -> Settings) = settingsPreferences.update(update)

    fun getSettingsLiveData(): MutableLiveData<Settings> = settingsPreferences.getSettingsLiveData()

    fun getSettings(): Settings = settingsPreferences.getSettings()


    // currency repo
    suspend fun selectCurrency(currencyName: String): Either<Unit, RepoErrors> {
        return callErrorsHandler(
            application = application,
            suspendedCall = currencyConverterApi::getCurrenciesValueNow
        )
        { currencyValues ->
            update {
                it.currancy = CurrencyUtil.getCurrency(currencyName).apply {
                    converterValue = when (idEnum) {
                        CurrenciesEnum.EGP -> currencyValues.egp
                        CurrenciesEnum.USD -> 1.0
                        CurrenciesEnum.EUR -> currencyValues.eur
                    }
                }
                it
            }
            Either.Success(Unit)
        }
    }

    suspend fun updateCurrency(): Either<Unit, RepoErrors> {
        return callErrorsHandler(
            application = application,
            suspendedCall = currencyConverterApi::getCurrenciesValueNow
        )
        { currencyValues ->
            update {
                it.currancy = CurrencyUtil.getCurrency(it.currancy.idEnum).apply {
                    converterValue = when (idEnum) {
                        CurrenciesEnum.EGP -> currencyValues.egp
                        CurrenciesEnum.USD -> 1.0
                        CurrenciesEnum.EUR -> currencyValues.eur
                    }
                }
                it
            }
            Either.Success(Unit)
        }
    }


    private suspend fun <S, R> callErrorsHandler(
        application: Application, suspendedCall: suspend () -> Response<S>,
        noErrors: suspend ((S) -> Either<R, RepoErrors>)
    ): Either<R, RepoErrors> {
        if (hasInternet(application)) {
            try {
                val response = suspendedCall()
                return if (response.isSuccessful) {
                    val data = response.body()
                    return if (data != null) {
                        noErrors(data)
                    } else {
                        Either.Error(RepoErrors.EmptyBody, "no Values was Found")
                    }
                } else {
                    Either.Error(RepoErrors.ServerError, response.message())
                }
            } catch (t: Throwable) {
                return Either.Error(RepoErrors.ServerError, t.message)
            }
        } else {
            return Either.Error(RepoErrors.NoInternetConnection, "no internet Connection")
        }
    }

}


//
//
//if (hasInternet(application)) {
//    val currencyValues = currencyConverterApi.getCurrenciesValueNow()
//    try {
//        return if (currencyValues.isSuccessful) {
//            return if (currencyValues.body() != null) {
//                update {
//                    it.apply {
//                        currancy = CurrencyUtil.getCurrency(currencyName).apply {
//                            if (idEnum == CurrenciesEnum.EGP)
//                                converterValue = currencyValues.body()?.egp ?: 1.0
//                            else if (idEnum == CurrenciesEnum.EUR)
//                                converterValue = currencyValues.body()?.eur ?: 1.0
//                        }
//                    }
//                }
//                Either.Success(Unit)
//            } else {
//                Either.Error(RepoErrors.EmptyBody, "no Values was Found")
//            }
//        } else {
//            Either.Error(RepoErrors.ServerError, currencyValues.message())
//        }
//    } catch (t: Throwable) {
//        return Either.Error(RepoErrors.ServerError, t.message)
//    }
//} else {
//    return Either.Error(RepoErrors.NoInternetConnection, "no internet Connection")
//}