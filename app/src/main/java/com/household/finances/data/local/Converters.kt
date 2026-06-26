package com.household.finances.data.local

import androidx.room.TypeConverter
import com.household.finances.data.model.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromAssetType(value: AssetType): String = value.name

    @TypeConverter
    fun toAssetType(value: String): AssetType = AssetType.valueOf(value)

    @TypeConverter
    fun fromLiabilityType(value: LiabilityType): String = value.name

    @TypeConverter
    fun toLiabilityType(value: String): LiabilityType = LiabilityType.valueOf(value)

    @TypeConverter
    fun fromCashFlowType(value: CashFlowType): String = value.name

    @TypeConverter
    fun toCashFlowType(value: String): CashFlowType = CashFlowType.valueOf(value)

    @TypeConverter
    fun fromInvestmentType(value: InvestmentType): String = value.name

    @TypeConverter
    fun toInvestmentType(value: String): InvestmentType = InvestmentType.valueOf(value)

    @TypeConverter
    fun fromInsuranceType(value: InsuranceType): String = value.name

    @TypeConverter
    fun toInsuranceType(value: String): InsuranceType = InsuranceType.valueOf(value)

    @TypeConverter
    fun fromGoalType(value: GoalType): String = value.name

    @TypeConverter
    fun toGoalType(value: String): GoalType = GoalType.valueOf(value)

    @TypeConverter
    fun fromGoalStatus(value: GoalStatus): String = value.name

    @TypeConverter
    fun toGoalStatus(value: String): GoalStatus = GoalStatus.valueOf(value)

    @TypeConverter
    fun fromStringList(value: List<String>): String = json.encodeToString(value)

    @TypeConverter
    fun toStringList(value: String): List<String> = json.decodeFromString(value)

    @TypeConverter
    fun fromMap(value: Map<String, Double>): String = json.encodeToString(value)

    @TypeConverter
    fun toMap(value: String): Map<String, Double> = json.decodeFromString(value)

    @TypeConverter
    fun fromAssetTypeMap(value: Map<AssetType, Double>): String {
        val stringMap = value.entries.associate { it.key.name to it.value }
        return json.encodeToString(stringMap)
    }

    @TypeConverter
    fun toAssetTypeMap(value: String): Map<AssetType, Double> {
        val stringMap: Map<String, Double> = json.decodeFromString(value)
        return stringMap.entries.associate { AssetType.valueOf(it.key) to it.value }
    }

    @TypeConverter
    fun fromLiabilityTypeMap(value: Map<LiabilityType, Double>): String {
        val stringMap = value.entries.associate { it.key.name to it.value }
        return json.encodeToString(stringMap)
    }

    @TypeConverter
    fun toLiabilityTypeMap(value: String): Map<LiabilityType, Double> {
        val stringMap: Map<String, Double> = json.decodeFromString(value)
        return stringMap.entries.associate { LiabilityType.valueOf(it.key) to it.value }
    }
}
