package com.household.finances.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "insurances")
data class Insurance(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val company: String,
    val type: InsuranceType,
    val coverageAmount: Double = 0.0,
    val annualPremium: Double = 0.0,
    val paymentYears: Int = 0,
    val startDate: Long = System.currentTimeMillis(),
    val insuredPerson: String = "",
    val description: String = "",
    val category: String = "",
    val tags: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Serializable
enum class InsuranceType {
    LIFE,       // 寿险
    HEALTH,     // 健康险
    ACCIDENT,   // 意外险
    INVESTMENT, // 理财险
    OTHER       // 其他
}
