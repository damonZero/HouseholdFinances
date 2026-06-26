package com.household.finances.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "assets")
data class Asset(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: AssetType,
    val value: Double,
    val purchasePrice: Double = 0.0,
    val purchaseDate: Long = System.currentTimeMillis(),
    val description: String = "",
    val category: String = "",
    val tags: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Serializable
enum class AssetType {
    REAL_ESTATE,    // 房产
    CASH,           // 现金
    INVESTMENT,     // 投资
    INSURANCE,      // 保险
    OTHER           // 其他
}
