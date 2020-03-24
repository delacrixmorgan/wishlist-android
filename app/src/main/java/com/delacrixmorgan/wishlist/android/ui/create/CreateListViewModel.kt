package com.delacrixmorgan.wishlist.android.ui.create

import androidx.lifecycle.ViewModel
import com.delacrixmorgan.wishlist.android.data.category.BudgetType
import com.delacrixmorgan.wishlist.android.data.controller.BudgetDataController
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.model.BudgetParams
import com.delacrixmorgan.wishlist.android.data.model.Money

class CreateListViewModel : ViewModel() {
    private var params = BudgetParams()

    fun isValidProceeding(): Boolean {
        return params.name != null || params.budgetType != null || params.amount != null
    }

    fun isActionButtonEnabled(currentPosition: Int): Boolean {
        return when (currentPosition) {
            0 -> !params.name.isNullOrEmpty()
            1 -> params.budgetType != null
            2 -> params.amount != null
            else -> false
        }
    }

    fun update(
        name: String? = null,
        type: BudgetType? = null,
        amount: Money? = null,
        isRolloverEnabled: Boolean? = null
    ) {
        name?.let { params.name = it }
        type?.let { params.budgetType = it }
        amount?.let { params.amount = it }
        isRolloverEnabled?.let { params.isRolloverEnabled = it }
    }

    suspend fun createBudget(completion: ((Budget) -> Unit)) {
        BudgetDataController.insertBudget(params) {
            completion.invoke(it)
        }
    }
}