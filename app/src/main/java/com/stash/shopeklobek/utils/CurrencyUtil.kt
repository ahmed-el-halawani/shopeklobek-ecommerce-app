package com.stash.shopeklobek.utils

import android.app.Application
import android.content.Context
import com.stash.shopeklobek.R
import com.stash.shopeklobek.model.entities.currencies.Currency
import com.stash.shopeklobek.model.entities.currencies.CurrencyConverterResult
import com.stash.shopeklobek.model.shareprefrances.CurrenciesEnum
import com.stash.shopeklobek.model.shareprefrances.SettingsPreferences

object CurrencyUtil {

    var application: Application? = null

    val currenciesList = listOf(
        Currency(
            "Egyptian Pound",
            application?.getString(R.string.egp_symbol) ?: "£",
            "EGP",
            CurrenciesEnum.EGP,
            R.string.egp_symbol
        ),

        Currency(
            "United States Dollar",
            application?.getString(R.string.usd_symbol) ?: """$""",
            "USD",
            CurrenciesEnum.USD,
                    R.string.usd_symbol

        ),

        Currency(
            "Saudi Riyal",
            application?.getString(R.string.sar_symbol) ?: "SAR",
            "SAR",
            CurrenciesEnum.SAR,
            R.string.sar_symbol
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

    fun convertCurrency(value: String?,context:Context): String {
        try {

            val application = context.applicationContext as Application
            val settings = SettingsPreferences.getInstance(application)
            val currency = settings.getSettings().currancy


            return (currency.converterValue * (value?.toDouble() ?: 0.0)).toFixed()
                .toString() + context.getString(currency.currencyResourceId)
        } catch (t: Throwable) {
            return value ?: "0"
        }
    }

    fun convertCurrency(value: Double?,context: Context): String {
        try {
            val application = context.applicationContext as Application

            val settings = SettingsPreferences.getInstance(application)
            val currency = settings.getSettings().currancy

            return (currency.converterValue * (value ?: 0.0)).toFixed()
                .toString() + context.getString(currency.currencyResourceId)
        } catch (t: Throwable) {
            return value.toString()
        }
    }


}



