package com.stash.shopeklobek.utils

import android.app.Application
import com.stash.shopeklobek.model.entities.currencies.Currency
import com.stash.shopeklobek.model.entities.currencies.CurrencyConverterResult
import com.stash.shopeklobek.model.shareprefrances.CurrenciesEnum
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences

object CurrencyUtil {

    var application: Application? = null

    val currenciesList = listOf(
        Currency(
            "Egyptian Pound", "£", "EGP", CurrenciesEnum.EGP
        ),

        Currency(
            "United States Dollar", "\$", "USD", CurrenciesEnum.USD
        ),

        Currency(
            "Saudi Riyal", "﷼", "SAR", CurrenciesEnum.SAR
        ),
    )

    fun updateCurrencyValue(c: CurrencyConverterResult) {
        currenciesList.forEach {
            it.converterValue = when (it.idEnum) {
                CurrenciesEnum.EGP -> 1.0
                CurrenciesEnum.SAR -> c.sar
                CurrenciesEnum.USD -> c.usd
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

    fun convertCurrency(value: String?): String {
        try {
            if (application == null) return value ?: "0"

            val settings = SettingsPreferences.getInstance(application!!)
            val currency = settings.getSettings().currancy


            return (currency.converterValue * (value?.toDouble() ?: 0.0)).toFixed().toString() + currency.currencySymbol
        } catch (t: Throwable) {
            return value ?: "0"
        }
    }

    fun convertCurrency(value: Double?): String {
        try {
            if (application == null) return value.toString()

            val settings = SettingsPreferences.getInstance(application!!)
            val currency = settings.getSettings().currancy

            return (currency.converterValue * (value ?: 0.0)).toFixed().toString() + currency.currencySymbol
        } catch (t: Throwable) {
            return value.toString()
        }
    }


}



