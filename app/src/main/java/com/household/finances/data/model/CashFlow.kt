package com.household.finances.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "cash_flows")
data class CashFlow(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long = System.currentTimeMillis(),
    val type: CashFlowType,
    val amount: Double,
    val category: String,
    val description: String = "",
    val account: String = "",
    val tags: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
)

@Serializable
enum class CashFlowType {
    INCOME,  // 收入
    EXPENSE  // 支出
}
