package com.delacrixmorgan.wishlist.android.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delacrixmorgan.wishlist.android.data.controller.BudgetDataController
import com.delacrixmorgan.wishlist.android.data.model.Budget
import kotlinx.coroutines.launch

class BudgetListViewModel : ViewModel() {
    var budget: Budget? = null

    fun deleteBudget(completion: ((Budget) -> Unit)) {
        viewModelScope.launch {
            BudgetDataController.deleteBudget(requireNotNull(budget), completion)
        }
    }
}