package com.delacrixmorgan.wishlist.android.data.mock

import com.delacrixmorgan.wishlist.android.common.generateDouble
import com.delacrixmorgan.wishlist.android.common.generateRandomDate
import com.delacrixmorgan.wishlist.android.data.category.BudgetType
import com.delacrixmorgan.wishlist.android.data.model.Money
import com.delacrixmorgan.wishlist.android.data.model.Transaction

object MockDataController {
    fun generateTransactions(budgetUuid: String): List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        val randomNames = listOf(
            "Sushi Tei", "Sukiya", "Petrol", "Gong Cha", "GSC Tickets",
            "Jay Chou Concert Tickets", "TnG Top Up", "Bitcoin Investment", "Starling Mall Parking",
            "Damansara Uptown Hokkien Mee"
        )
        randomNames.forEachIndexed { index, name ->
            val amount = Money(generateDouble().toDouble())
            transactions.add(
                Transaction(
                    budgetUuid = budgetUuid,
                    budgetType = BudgetType.General,
                    amountTransaction = amount,
                    name = name,
                    createdAt = generateRandomDate()
                )
            )
        }
        transactions.sortBy { it.createdAt }
        return transactions
    }
}