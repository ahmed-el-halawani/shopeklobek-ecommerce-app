package com.stash.shopeklobek.utils

import com.stash.shopeklobek.model.entities.currencies.Currency
import com.stash.shopeklobek.model.entities.currencies.CurrencyConverterResult
import com.stash.shopeklobek.model.shareprefrances.CurrenciesEnum

object CurrencyUtil {

    val currenciesList = listOf(
        Currency(
            "Egyptian Pound", "£", "EGP", CurrenciesEnum.EGP
        ),

        Currency(
            "United States Dollar", "\$", "USD", CurrenciesEnum.USD
        ),

        Currency(
            "Euro", "€", "EUR", CurrenciesEnum.EUR
        ),
    )

    fun updateCurrencyValue(c: CurrencyConverterResult){
        currenciesList.forEach {
            it.converterValue = when(it.idEnum){
                CurrenciesEnum.EGP -> c.egp
                CurrenciesEnum.EUR -> c.eur
                else ->  1.0
            }
        }
    }

    val currenciesNamesList = currenciesList.map { t -> t.currencyName }

    fun getCurrency(currencyName: String): Currency {
        return currenciesList.first {
            it.currencyName == currencyName
        }
    }

    fun getCurrency(id: CurrenciesEnum): Currency {
        return currenciesList.first {
            it.idEnum == id
        }
    }


}



