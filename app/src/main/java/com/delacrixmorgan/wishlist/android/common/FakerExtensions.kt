package com.delacrixmorgan.wishlist.android.common

import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Date
 */

fun generateRandomDate(startDate: Date? = null, endDate: Date? = null): Date {
    val startDateTimeInLong = Calendar.getInstance().apply {
        if (startDate != null) {
            time = startDate
        } else {
            set(Calendar.MONTH, 1)
            set(Calendar.DAY_OF_MONTH, 1)
        }
    }.timeInMillis
    val endDateTimeInLong = Calendar.getInstance().apply {
        if (endDate != null) {
            time = endDate
        }
    }.timeInMillis
    return Date(ThreadLocalRandom.current().nextLong(startDateTimeInLong, endDateTimeInLong))
}

/**
 * Int
 */

fun generateDouble(start: Int? = null, end: Int? = null): Int {
    require(start ?: 1 <= end ?: 100)
    return (requireNotNull(start ?: 1)..requireNotNull(end ?: 100)).random()
}