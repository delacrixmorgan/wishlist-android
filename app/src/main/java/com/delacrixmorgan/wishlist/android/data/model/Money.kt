package com.delacrixmorgan.wishlist.android.data.model

import com.delacrixmorgan.wishlist.android.data.controller.CurrencyDataController

data class Money(
    var amount: Double,
    var currency: Currency = CurrencyDataController.selectedCurrency
) {
    operator fun plus(targetMoney: Money): Money {
        return Money(amount + targetMoney.amount)
    }

    operator fun minus(targetMoney: Money): Money {
        return Money(amount - targetMoney.amount)
    }
}