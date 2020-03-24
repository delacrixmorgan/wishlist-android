package com.delacrixmorgan.wishlist.android.ui.create

import com.delacrixmorgan.wishlist.android.data.category.BudgetType
import com.delacrixmorgan.wishlist.android.data.model.Money

interface CreateListeners {
    fun onBudgetNameUpdate(name: String)
    fun onBudgetTypeSelected(budgetType: BudgetType)
    fun onBudgetAmountSelected(amount: Money)
    fun onBudgetRolloverToggled(isEnabled: Boolean)
}