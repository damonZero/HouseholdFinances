package com.household.finances.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "investments")
data class Investment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: InvestmentType,
    val symbol: String = "",
    val shares: Double = 0.0,
    val purchasePrice: Double = 0.0,
    val currentPrice: Double = 0.0,
    val purchaseDate: Long = System.currentTimeMillis(),
    val description: String = "",
    val category: String = "",
    val tags: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Serializable
enum class InvestmentType {
    ETF,        // ETF
    FUND,       // 基金
    STOCK,      // 股票
    BOND,       // 债券
    OTHER       // 其他
}
