package com.delacrixmorgan.wishlist.android.common

import java.util.*

object DateUtils {
    val lastDay: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1
        }

    val currentDay: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.DAY_OF_MONTH)
        }

    val daysLeft: Int
        get() {
            val calendar = Calendar.getInstance()
            val lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            return lastDay - currentDay
        }
}