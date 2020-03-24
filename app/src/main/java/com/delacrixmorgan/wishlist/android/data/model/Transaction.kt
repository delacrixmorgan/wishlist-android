package com.delacrixmorgan.wishlist.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.delacrixmorgan.wishlist.android.data.category.BudgetType
import com.delacrixmorgan.wishlist.android.data.controller.BudgetDataController
import java.util.*

@Entity
data class Transaction(
    @PrimaryKey val uuid: String = UUID.randomUUID().toString(),
    val budgetUuid: String,
    val budgetType: BudgetType,
    var amountTransaction: Money,
    var name: String,
    var createdAt: Date = Date()
) {
    val budget: Budget?
        get() {
            return BudgetDataController.getBudgetById(budgetUuid)
        }
}