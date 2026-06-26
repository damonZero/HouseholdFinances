package com.household.finances.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "financial_goals")
data class FinancialGoal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: GoalType,
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val deadline: Long = System.currentTimeMillis(),
    val description: String = "",
    val status: GoalStatus = GoalStatus.ACTIVE,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Serializable
enum class GoalType {
    SAVINGS,        // 储蓄目标
    INVESTMENT,     // 投资目标
    DEBT_PAYOFF,    // 债务偿还
    RETIREMENT      // 退休规划
}

@Serializable
enum class GoalStatus {
    ACTIVE,         // 进行中
    COMPLETED,      // 已完成
    PAUSED,         // 已暂停
    CANCELLED       // 已取消
}
