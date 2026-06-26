package com.household.finances.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "asset_snapshots")
data class AssetSnapshot(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long = System.currentTimeMillis(),
    val totalAssets: Double = 0.0,
    val totalLiabilities: Double = 0.0,
    val netWorth: Double = 0.0,
    val assetBreakdown: Map<AssetType, Double> = emptyMap(),
    val liabilityBreakdown: Map<LiabilityType, Double> = emptyMap()
)
