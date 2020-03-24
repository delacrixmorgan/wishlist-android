package com.delacrixmorgan.wishlist.android.data.room.dao

import androidx.room.*

@Dao
interface TransactionDataDao {
    @Query("SELECT * from `Transaction`")
    suspend fun get(): List<com.delacrixmorgan.wishlist.android.data.model.Transaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: com.delacrixmorgan.wishlist.android.data.model.Transaction)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(transaction: com.delacrixmorgan.wishlist.android.data.model.Transaction)

    @Query("DELETE from `Transaction`")
    suspend fun delete()

    @Query("DELETE FROM `Transaction` WHERE uuid = :uuid")
    suspend fun deleteById(uuid: String)
}