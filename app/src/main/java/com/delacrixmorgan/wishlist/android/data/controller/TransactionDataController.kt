package com.delacrixmorgan.wishlist.android.data.controller

import com.delacrixmorgan.wishlist.android.App
import com.delacrixmorgan.wishlist.android.common.getCalendarField
import com.delacrixmorgan.wishlist.android.data.category.BudgetType
import com.delacrixmorgan.wishlist.android.data.mock.MockDataController
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.model.Money
import com.delacrixmorgan.wishlist.android.data.model.SortType
import com.delacrixmorgan.wishlist.android.data.model.Transaction
import com.delacrixmorgan.wishlist.android.data.room.database.TransactionDatabase
import java.util.*

object TransactionDataController {
    var transactions = mutableListOf<Transaction>()

    suspend fun loadTransaction() {
        val dao = TransactionDatabase.getInstance(App.appContext)?.transactionDao()
        val daoTransactions = (dao?.get() as MutableList<Transaction>?) ?: mutableListOf()

//        if (daoTransactions.isEmpty()) {
//            daoTransactions.addAll(
//                MockDataController.generateTransactions(BudgetDataController.budgets.first().uuid)
//            )
//            daoTransactions.forEach { dao?.insert(it) }
//        }
        transactions = daoTransactions
    }

    suspend fun insertTransaction(transaction: Transaction, completion: (Transaction) -> Unit) {
        val dao = TransactionDatabase.getInstance(App.appContext)?.transactionDao()
        transactions.add(transaction)

        dao?.insert(transaction)
        completion.invoke(transaction)
    }

    suspend fun editTransaction(transaction: Transaction, completion: (Transaction) -> Unit) {

    }

    suspend fun deleteTransaction(transaction: Transaction, completion: (Transaction) -> Unit) {

    }

    fun getTransactionById(uuid: String): Transaction? {
        return transactions.firstOrNull { it.uuid == uuid }
    }

    fun getTransaction(
        budget: Budget? = null,
        budgetType: BudgetType? = null,
        sortType: SortType? = null
    ): List<Transaction> {
        var filteredList = transactions

        budget?.let {
            filteredList = filteredList.filter { it.budgetUuid == budget.uuid }.toMutableList()
        }

        budgetType?.let {
            filteredList = filteredList.filter { it.budgetType == budgetType }.toMutableList()
        }

        when (sortType) {
            SortType.DateAscending -> filteredList.sortBy { it.createdAt }
            SortType.DateDescending -> filteredList.sortByDescending { it.createdAt }
        }

        return filteredList
    }

    fun getCurrentMonthTransactionAmount(budget: Budget): Money {
        var filteredList = transactions
        val calendar = Calendar.getInstance()

        filteredList = filteredList.filter {
            it.budgetUuid == budget.uuid &&
                    it.createdAt.getCalendarField(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                    it.createdAt.getCalendarField(Calendar.MONTH) == calendar.get(Calendar.MONTH)
        }.toMutableList()

        return Money(filteredList.sumByDouble { it.amountTransaction.amount })
    }
}