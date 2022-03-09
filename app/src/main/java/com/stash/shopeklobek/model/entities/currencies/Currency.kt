package com.stash.shopeklobek.model.entities.currencies

import com.stash.shopeklobek.model.shareprefrances.CurrenciesEnum

data class Currency(
    val currencyName: String,
    val currencySymbol: String,
    val id: String,
    val idEnum: CurrenciesEnum,
    val currencyResourceId: Int,
    var converterValue: Double=1.0,

    ){
        fun convertIt(value:Double):Double{
            return value*converterValue
        }
    }