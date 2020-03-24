package com.delacrixmorgan.wishlist.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.delacrixmorgan.wishlist.android.data.category.BudgetType
import com.delacrixmorgan.wishlist.android.data.controller.TransactionDataController
import java.util.*

@Entity
data class Budget(
    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
    val name: String,
    var type: BudgetType,
    var amountTotal: Money,
    var isRolloverEnabled: Boolean,
    var createdAt: Date = Date(),
    var updatedAt: Date = Date()
) {
    val amountSpent: Money
        get() {
            return TransactionDataController.getCurrentMonthTransactionAmount(this)
        }
    val amountLeft: Money
        get() = Money(amountTotal.amount - amountSpent.amount)
}

data class BudgetParams(
    var name: String? = null,
    var budgetType: BudgetType? = BudgetType.General,
    var amount: Money? = null,
    var isRolloverEnabled: Boolean = false
)

data class BudgetRefreshEvent(
    val budget: Budget
)