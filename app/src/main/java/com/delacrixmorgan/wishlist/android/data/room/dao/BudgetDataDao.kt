package com.delacrixmorgan.wishlist.android.data.room.dao

import androidx.room.*
import com.delacrixmorgan.wishlist.android.data.model.Budget

@Dao
interface BudgetDataDao {
    @Query("SELECT * from Budget")
    suspend fun get(): List<Budget>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: Budget)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(budget: Budget)

    @Query("DELETE FROM Budget WHERE uuid = :uuid")
    suspend fun deleteById(uuid: String)
}