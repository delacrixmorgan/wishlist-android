package com.delacrixmorgan.wishlist.android.longform

import com.delacrixmorgan.wishlist.android.common.ListenerProtocol
import com.delacrixmorgan.wishlist.android.data.model.Transaction

interface LongFormListener : ListenerProtocol {
    fun onLongFormSubmitted(transaction: Transaction)
}