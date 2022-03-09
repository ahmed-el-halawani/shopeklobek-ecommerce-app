package com.stash.shopeklobek.model.entities.currencies

data class ExchangerApiResponse(
    val base_code: String,
    val conversion_rates: ConversionRates,
)