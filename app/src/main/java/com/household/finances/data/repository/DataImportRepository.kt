package com.household.finances.data.repository

import android.content.Context
import com.household.finances.data.model.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataImportRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val assetRepository: AssetRepository,
    private val liabilityRepository: LiabilityRepository,
    private val cashFlowRepository: CashFlowRepository
) {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    suspend fun importAssetsFromCsv(file: File): ImportResult {
        return try {
            val assets = mutableListOf<Asset>()
            var errorCount = 0

            withContext(Dispatchers.IO) {
                val reader = file.bufferedReader()
                val header = reader.readLine() // Skip header

                reader.forEachLine { line ->
                    try {
                        val parts = parseCsvLine(line)
                        if (parts.size >= 4) {
                            val asset = Asset(
                                name = parts[1],
                                type = AssetType.valueOf(parts[2]),
                                value = parts[3].toDoubleOrNull() ?: 0.0,
                                purchasePrice = parts.getOrNull(4)?.toDoubleOrNull() ?: 0.0,
                                purchaseDate = parts.getOrNull(5)?.let {
                                    try { dateFormat.parse(it)?.time } catch (e: Exception) { null }
                                } ?: System.currentTimeMillis(),
                                description = parts.getOrNull(6)?.trim('"') ?: "",
                                category = parts.getOrNull(7)?.trim('"') ?: ""
                            )
                            assets.add(asset)
                        }
                    } catch (e: Exception) {
                        errorCount++
                    }
                }
                reader.close()
            }

            // Insert assets in coroutine context
            var successCount = 0
            for (asset in assets) {
                try {
                    assetRepository.insertAsset(asset)
                    successCount++
                } catch (e: Exception) {
                    errorCount++
                }
            }

            ImportResult(
                success = true,
                successCount = successCount,
                errorCount = errorCount,
                message = "导入完成: 成功 $successCount 条, 失败 $errorCount 条"
            )
        } catch (e: Exception) {
            ImportResult(
                success = false,
                message = "导入失败: ${e.message}"
            )
        }
    }

    suspend fun importLiabilitiesFromCsv(file: File): ImportResult {
        return try {
            val liabilities = mutableListOf<Liability>()
            var errorCount = 0

            withContext(Dispatchers.IO) {
                val reader = file.bufferedReader()
                val header = reader.readLine() // Skip header

                reader.forEachLine { line ->
                    try {
                        val parts = parseCsvLine(line)
                        if (parts.size >= 4) {
                            val liability = Liability(
                                name = parts[1],
                                type = LiabilityType.valueOf(parts[2]),
                                amount = parts[3].toDoubleOrNull() ?: 0.0,
                                interestRate = parts.getOrNull(4)?.toDoubleOrNull() ?: 0.0,
                                monthlyPayment = parts.getOrNull(5)?.toDoubleOrNull() ?: 0.0,
                                remainingMonths = parts.getOrNull(6)?.toIntOrNull() ?: 0,
                                startDate = parts.getOrNull(7)?.let {
                                    try { dateFormat.parse(it)?.time } catch (e: Exception) { null }
                                } ?: System.currentTimeMillis(),
                                description = parts.getOrNull(8)?.trim('"') ?: ""
                            )
                            liabilities.add(liability)
                        }
                    } catch (e: Exception) {
                        errorCount++
                    }
                }
                reader.close()
            }

            // Insert liabilities in coroutine context
            var successCount = 0
            for (liability in liabilities) {
                try {
                    liabilityRepository.insertLiability(liability)
                    successCount++
                } catch (e: Exception) {
                    errorCount++
                }
            }

            ImportResult(
                success = true,
                successCount = successCount,
                errorCount = errorCount,
                message = "导入完成: 成功 $successCount 条, 失败 $errorCount 条"
            )
        } catch (e: Exception) {
            ImportResult(
                success = false,
                message = "导入失败: ${e.message}"
            )
        }
    }

    suspend fun importAllDataFromJson(file: File): ImportResult = withContext(Dispatchers.IO) {
        try {
            val jsonString = file.readText()
            val root = JSONObject(jsonString)
            var successCount = 0
            var errorCount = 0

            // Import assets
            root.optJSONArray("assets")?.let { array ->
                for (i in 0 until array.length()) {
                    try {
                        val obj = array.getJSONObject(i)
                        val asset = Asset(
                            name = obj.getString("name"),
                            type = AssetType.valueOf(obj.getString("type")),
                            value = obj.getDouble("value"),
                            purchasePrice = obj.optDouble("purchasePrice", 0.0),
                            description = obj.optString("description", ""),
                            category = obj.optString("category", "")
                        )
                        assetRepository.insertAsset(asset)
                        successCount++
                    } catch (e: Exception) {
                        errorCount++
                    }
                }
            }

            // Import liabilities
            root.optJSONArray("liabilities")?.let { array ->
                for (i in 0 until array.length()) {
                    try {
                        val obj = array.getJSONObject(i)
                        val liability = Liability(
                            name = obj.getString("name"),
                            type = LiabilityType.valueOf(obj.getString("type")),
                            amount = obj.getDouble("amount"),
                            interestRate = obj.optDouble("interestRate", 0.0),
                            monthlyPayment = obj.optDouble("monthlyPayment", 0.0),
                            remainingMonths = obj.optInt("remainingMonths", 0),
                            description = obj.optString("description", "")
                        )
                        liabilityRepository.insertLiability(liability)
                        successCount++
                    } catch (e: Exception) {
                        errorCount++
                    }
                }
            }

            // Import cash flows
            root.optJSONArray("cashFlows")?.let { array ->
                for (i in 0 until array.length()) {
                    try {
                        val obj = array.getJSONObject(i)
                        val cashFlow = CashFlow(
                            type = CashFlowType.valueOf(obj.getString("type")),
                            amount = obj.getDouble("amount"),
                            category = obj.getString("category"),
                            description = obj.optString("description", ""),
                            account = obj.optString("account", "")
                        )
                        cashFlowRepository.insertCashFlow(cashFlow)
                        successCount++
                    } catch (e: Exception) {
                        errorCount++
                    }
                }
            }

            ImportResult(
                success = true,
                successCount = successCount,
                errorCount = errorCount,
                message = "导入完成: 成功 $successCount 条, 失败 $errorCount 条"
            )
        } catch (e: Exception) {
            ImportResult(
                success = false,
                message = "导入失败: ${e.message}"
            )
        }
    }

    private fun parseCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        var current = StringBuilder()
        var inQuotes = false

        for (char in line) {
            when {
                char == '"' -> inQuotes = !inQuotes
                char == ',' && !inQuotes -> {
                    result.add(current.toString())
                    current = StringBuilder()
                }
                else -> current.append(char)
            }
        }
        result.add(current.toString())
        return result
    }
}

data class ImportResult(
    val success: Boolean,
    val successCount: Int = 0,
    val errorCount: Int = 0,
    val message: String
)
