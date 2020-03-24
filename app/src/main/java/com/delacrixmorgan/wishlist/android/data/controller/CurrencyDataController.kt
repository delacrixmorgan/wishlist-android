package com.delacrixmorgan.wishlist.android.data.controller

import com.delacrixmorgan.wishlist.android.data.model.Currency

object CurrencyDataController {
    private val currencies = listOf(
        Currency("Malaysian Ringgit", "MYR", "RM"),
        Currency("United States Dollar", "USD", "$")
    )

    var selectedCurrency = currencies.first()

    fun getCurrency(currencyIsoCode: String? = null, symbol: String? = null): Currency? {
        currencyIsoCode?.let {
            return currencies.firstOrNull { it.currencyIsoCode == currencyIsoCode }
        }

        symbol?.let {
            return currencies.firstOrNull { it.symbol == symbol }
        }

        return currencies.first()
    }
}