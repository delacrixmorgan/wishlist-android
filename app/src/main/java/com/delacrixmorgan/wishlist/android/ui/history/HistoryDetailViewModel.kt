package com.delacrixmorgan.wishlist.android.ui.history

import androidx.lifecycle.ViewModel
import com.delacrixmorgan.wishlist.android.data.controller.TransactionDataController
import com.delacrixmorgan.wishlist.android.data.model.Transaction

class HistoryDetailViewModel : ViewModel() {
    var transaction: Transaction? = null

    suspend fun editTransaction(completion: ((Transaction) -> Unit)) {
        TransactionDataController.editTransaction(requireNotNull(transaction), completion)
    }

    suspend fun deleteTransaction(completion: ((Transaction) -> Unit)) {
        TransactionDataController.deleteTransaction(requireNotNull(transaction), completion)
    }
}