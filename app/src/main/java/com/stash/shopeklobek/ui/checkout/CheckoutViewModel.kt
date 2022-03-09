package com.stash.shopeklobek.ui.checkout

import android.app.Application
import androidx.lifecycle.*
import com.orhanobut.hawk.Hawk
import com.stash.shopeklobek.model.api.ShopifyApi
import com.stash.shopeklobek.model.entities.Address
import com.stash.shopeklobek.model.entities.FinancialStatus
import com.stash.shopeklobek.model.entities.Order
import com.stash.shopeklobek.model.entities.PriceRule
import com.stash.shopeklobek.model.entities.room.RoomCart
import com.stash.shopeklobek.model.repositories.ProductRepo
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences
import com.stash.shopeklobek.model.utils.Either
import com.stash.shopeklobek.model.utils.RepoErrors
import com.stash.shopeklobek.model.utils.RoomAddOrderErrors
import com.stash.shopeklobek.utils.ViewHelpers
import com.stash.shopeklobek.utils.getPrice
import com.stash.shopeklobek.utils.observeOnce
import com.stash.shopeklobek.utils.toCurrency
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

enum class PaymentMethodsEnum {
    Cash, Paypal
}

class CheckoutViewModel(val app: Application, val productRepo: ProductRepo) :
    AndroidViewModel(app) {

    var cartProducts: List<RoomCart> = emptyList()
    var priceRule: PriceRule? = null
    var selectedAddress: Address? = null
    var shipping = 5.0
    var selectedPaymentMethods: PaymentMethodsEnum = PaymentMethodsEnum.Cash
    var fixedDiscountLiveData = MutableLiveData<Either<PriceRule, RepoErrors>?>(null)


    fun addDiscount(discountCode: String) = viewModelScope.launch {
        fixedDiscountLiveData.value = productRepo.getDiscount(discountCode).also {
            priceRule = when (it) {
                is Either.Error -> null
                is Either.Success -> it.data
            }
        }
    }

    fun removeDiscount(){
        priceRule = null
        fixedDiscountLiveData.postValue(null)
    }

    suspend fun confirm(): Either<Unit, RoomAddOrderErrors> {
        val order = Order(
            finalPrice = cartProducts.getPrice().toCurrency(app),
            createdAt = Date().time,
            billingAddress = selectedAddress,
            totalDiscount = priceRule?.value,
            items = cartProducts,
        )

        return when (selectedPaymentMethods) {
            PaymentMethodsEnum.Cash -> {
                productRepo.addOrder(order.copy(financialStatus = FinancialStatus.Voided.value))
            }
            PaymentMethodsEnum.Paypal -> {
                productRepo.addOrder(order.copy(financialStatus = FinancialStatus.Paid.value))
            }
        }
    }


    init {
        productRepo.getCart().observeOnce {
            cartProducts = it
        }
    }

    val loadingLiveData = MutableLiveData(false)

    val pageIndexLiveData = MutableLiveData(0)

    val products: LiveData<List<RoomCart>> by lazy {
        productRepo.getCart()
    }

    private suspend fun <T> loadingBloc(func: suspend () -> T): T {
        var res: T? = null
        loadingLiveData.value = true
        res = func()
        loadingLiveData.value = false
        return res
    }

    class Factory(private val application: Application, val productRepo: ProductRepo) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CheckoutViewModel(application, productRepo) as T
        }
    }

    companion object {
        fun create(context: CheckoutActivity): CheckoutViewModel {
            return ViewModelProvider(
                context,
                Factory(
                    context.applicationContext as Application,
                    ProductRepo(
                        ShopifyApi.api,
                        SettingsPreferences.getInstance(context.applicationContext as Application),
                        context.applicationContext as Application
                    )
                )
            )[CheckoutViewModel::class.java]
        }
    }

}