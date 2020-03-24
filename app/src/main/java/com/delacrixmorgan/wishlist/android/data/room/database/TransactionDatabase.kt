package com.delacrixmorgan.wishlist.android.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.delacrixmorgan.wishlist.android.data.model.Transaction
import com.delacrixmorgan.wishlist.android.data.room.Converters
import com.delacrixmorgan.wishlist.android.data.room.dao.TransactionDataDao

@Database(entities = [(Transaction::class)], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDataDao

    companion object {
        private var INSTANCE: TransactionDatabase? = null

        fun getInstance(context: Context): TransactionDatabase? {
            if (INSTANCE == null) {
                synchronized(TransactionDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TransactionDatabase::class.java,
                        "transaction.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}