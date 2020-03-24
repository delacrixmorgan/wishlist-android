package com.delacrixmorgan.wishlist.android.data.model

import com.delacrixmorgan.wishlist.android.data.category.TransportType

data class TransportModel(
    val budget: Budget,
    var type: TransportType,
    var remark: String
)