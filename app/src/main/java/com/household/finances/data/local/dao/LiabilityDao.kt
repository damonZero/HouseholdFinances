package com.household.finances.data.local.dao

import androidx.room.*
import com.household.finances.data.model.Liability
import kotlinx.coroutines.flow.Flow

@Dao
interface LiabilityDao {
    @Query("SELECT * FROM liabilities ORDER BY updatedAt DESC")
    fun getAllLiabilities(): Flow<List<Liability>>

    @Query("SELECT * FROM liabilities WHERE type = :type ORDER BY updatedAt DESC")
    fun getLiabilitiesByType(type: String): Flow<List<Liability>>

    @Query("SELECT * FROM liabilities WHERE id = :id")
    suspend fun getLiabilityById(id: Long): Liability?

    @Query("SELECT SUM(amount) FROM liabilities")
    fun getTotalLiabilities(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM liabilities WHERE type = :type")
    fun getTotalLiabilitiesByType(type: String): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLiability(liability: Liability): Long

    @Update
    suspend fun updateLiability(liability: Liability)

    @Delete
    suspend fun deleteLiability(liability: Liability)

    @Query("DELETE FROM liabilities WHERE id = :id")
    suspend fun deleteLiabilityById(id: Long)
}
