package com.delacrixmorgan.wishlist.android.common

interface FormProtocol<Model> {
    fun configure(model: Model?, listener: ListenerProtocol? = null)
    fun model(): Model?

    fun isValid() = true
}