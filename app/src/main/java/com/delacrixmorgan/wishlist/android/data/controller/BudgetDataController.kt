package com.delacrixmorgan.wishlist.android.data.controller

import com.delacrixmorgan.wishlist.android.App
import com.delacrixmorgan.wishlist.android.data.category.BudgetType
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.model.BudgetParams
import com.delacrixmorgan.wishlist.android.data.model.Money
import com.delacrixmorgan.wishlist.android.data.room.database.BudgetDatabase
import org.greenrobot.eventbus.EventBus

object BudgetDataController {
    var budgets = mutableListOf<Budget>()

    suspend fun loadBudget() {
        val dao = BudgetDatabase.getInstance(App.appContext)?.budgetDao()
        val daoBudgets = (dao?.get() as MutableList<Budget>?) ?: mutableListOf()

//        if (daoBudgets.isEmpty()) {
//            daoBudgets.addAll(
//                mutableListOf(
//                    Budget(
//                        name = "General",
//                        type = BudgetType.General,
//                        amountTotal = Money(500.0),
//                        isRolloverEnabled = true
//                    ),
//                    Budget(
//                        name = "Food",
//                        type = BudgetType.Food,
//                        amountTotal = Money(500.0),
//                        isRolloverEnabled = false
//                    ),
//                    Budget(
//                        name = "Transport",
//                        type = BudgetType.Transport,
//                        amountTotal = Money(500.0),
//                        isRolloverEnabled = false
//                    )
//                )
//            )
//            daoBudgets.forEach {
//                dao?.insert(it)
//            }
//        }
        budgets = daoBudgets
    }

    suspend fun insertBudget(params: BudgetParams, completion: (Budget) -> Unit) {
        val dao = BudgetDatabase.getInstance(App.appContext)?.budgetDao()
        val budget = Budget(
            name = requireNotNull(params.name),
            type = requireNotNull(params.budgetType),
            amountTotal = requireNotNull(params.amount),
            isRolloverEnabled = requireNotNull(params.isRolloverEnabled)
        )

        budgets.add(budget)
        dao?.insert(budget)

        completion.invoke(budget)
        EventBus.getDefault().post(budget)
    }

    suspend fun deleteBudget(budget: Budget, completion: (Budget) -> Unit){
        val dao = BudgetDatabase.getInstance(App.appContext)?.budgetDao()
        dao?.deleteById(budget.uuid)
        budgets.remove(budget)
        completion.invoke(budget)
    }

    suspend fun updateBudget(budget: Budget) {
        val dao = BudgetDatabase.getInstance(App.appContext)?.budgetDao()
        dao?.update(budget)
    }

    fun getBudgetById(uuid: String): Budget? {
        return budgets.firstOrNull { it.uuid == uuid }
    }

    fun getBudget(): List<Budget> {
        var filteredList = budgets
        return filteredList
    }
}