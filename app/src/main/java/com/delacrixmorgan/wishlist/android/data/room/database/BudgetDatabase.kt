package com.delacrixmorgan.wishlist.android.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.delacrixmorgan.wishlist.android.data.model.Budget
import com.delacrixmorgan.wishlist.android.data.room.Converters
import com.delacrixmorgan.wishlist.android.data.room.dao.BudgetDataDao

@Database(entities = [(Budget::class)], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDataDao

    companion object {
        private var INSTANCE: BudgetDatabase? = null

        fun getInstance(context: Context): BudgetDatabase? {
            if (INSTANCE == null) {
                synchronized(BudgetDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BudgetDatabase::class.java,
                        "budget.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}