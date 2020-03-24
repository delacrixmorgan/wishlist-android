package com.delacrixmorgan.wishlist.android.data.room

import androidx.room.TypeConverter
import com.delacrixmorgan.wishlist.android.data.category.BudgetType
import com.delacrixmorgan.wishlist.android.data.model.Money
import java.util.*

class Converters {
    @TypeConverter
    fun toDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toBudgetType(value: Int?): BudgetType? {
        return value?.let { BudgetType.values()[value] }
    }

    @TypeConverter
    fun fromBudgetType(type: BudgetType?): Int? {
        return type?.ordinal
    }

    @TypeConverter
    fun toMoney(value: Double?): Money? {
        return value?.let { Money(it) }
    }

    @TypeConverter
    fun fromMoney(money: Money?): Double? {
        return money?.amount
    }
}