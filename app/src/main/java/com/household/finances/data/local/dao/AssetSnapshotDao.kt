package com.household.finances.data.local.dao

import androidx.room.*
import com.household.finances.data.model.AssetSnapshot
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetSnapshotDao {
    @Query("SELECT * FROM asset_snapshots ORDER BY date DESC")
    fun getAllSnapshots(): Flow<List<AssetSnapshot>>

    @Query("SELECT * FROM asset_snapshots ORDER BY date DESC LIMIT :limit")
    fun getRecentSnapshots(limit: Int): Flow<List<AssetSnapshot>>

    @Query("SELECT * FROM asset_snapshots WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getSnapshotsByDateRange(startDate: Long, endDate: Long): Flow<List<AssetSnapshot>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSnapshot(snapshot: AssetSnapshot): Long

    @Delete
    suspend fun deleteSnapshot(snapshot: AssetSnapshot)
}
