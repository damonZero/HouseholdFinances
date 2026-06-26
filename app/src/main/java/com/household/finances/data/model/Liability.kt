package com.household.finances.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "liabilities")
data class Liability(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: LiabilityType,
    val amount: Double,
    val interestRate: Double = 0.0,
    val monthlyPayment: Double = 0.0,
    val remainingMonths: Int = 0,
    val startDate: Long = System.currentTimeMillis(),
    val description: String = "",
    val category: String = "",
    val tags: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Serializable
enum class LiabilityType {
    MORTGAGE,       // 房贷
    CAR_LOAN,       // 车贷
    CREDIT_CARD,    // 信用卡
    CONSUMER_LOAN,  // 消费贷
    OTHER           // 其他
}
