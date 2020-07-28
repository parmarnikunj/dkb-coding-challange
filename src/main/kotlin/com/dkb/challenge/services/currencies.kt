package com.dkb.challenge.services

import com.dkb.challenge.exceptions.NoCurrencySupportedException


object CurrencyService {

    // can come dynamically from some external service
    val currencyRateOfBank = hashMapOf(
            "USD" to 0.852437,
            "IND" to 0.011,
            "GBP" to 0.906281
    )

    fun convert(amount: Double, currencyCode: String): Double {
        return currencyRateOfBank[currencyCode]?.let { amount.times(it) } ?: throw NoCurrencySupportedException()
    }
}