package com.household.finances.data.repository

import android.content.Context
import com.household.finances.data.model.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataExportRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val assetRepository: AssetRepository,
    private val liabilityRepository: LiabilityRepository,
    private val cashFlowRepository: CashFlowRepository
) {
    private val json = Json { prettyPrint = true }

    suspend fun exportAssetsToCsv(): File = withContext(Dispatchers.IO) {
        val file = createExportFile("assets")
        val assetsList = assetRepository.getAllAssets().first()

        FileWriter(file).use { writer ->
            writer.appendLine("ID,名称,类型,当前价值,购入价格,购入日期,描述,分类")
            assetsList.forEach { asset ->
                writer.appendLine(
                    "${asset.id}," +
                    "\"${asset.name}\"," +
                    "${asset.type.name}," +
                    "${asset.value}," +
                    "${asset.purchasePrice}," +
                    "${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(asset.purchaseDate))}," +
                    "\"${asset.description}\"," +
                    "\"${asset.category}\""
                )
            }
        }
        file
    }

    suspend fun exportLiabilitiesToCsv(): File = withContext(Dispatchers.IO) {
        val file = createExportFile("liabilities")
        val liabilitiesList = liabilityRepository.getAllLiabilities().first()

        FileWriter(file).use { writer ->
            writer.appendLine("ID,名称,类型,金额,利率,月供,剩余期限,开始日期,描述")
            liabilitiesList.forEach { liability ->
                writer.appendLine(
                    "${liability.id}," +
                    "\"${liability.name}\"," +
                    "${liability.type.name}," +
                    "${liability.amount}," +
                    "${liability.interestRate}," +
                    "${liability.monthlyPayment}," +
                    "${liability.remainingMonths}," +
                    "${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(liability.startDate))}," +
                    "\"${liability.description}\""
                )
            }
        }
        file
    }

    suspend fun exportCashFlowsToCsv(): File = withContext(Dispatchers.IO) {
        val file = createExportFile("cashflows")
        val cashFlowsList = cashFlowRepository.getAllCashFlows().first()

        FileWriter(file).use { writer ->
            writer.appendLine("ID,日期,类型,金额,分类,描述,账户")
            cashFlowsList.forEach { cashFlow ->
                writer.appendLine(
                    "${cashFlow.id}," +
                    "${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(cashFlow.date))}," +
                    "${cashFlow.type.name}," +
                    "${cashFlow.amount}," +
                    "\"${cashFlow.category}\"," +
                    "\"${cashFlow.description}\"," +
                    "\"${cashFlow.account}\""
                )
            }
        }
        file
    }

    suspend fun exportAllDataToJson(): File = withContext(Dispatchers.IO) {
        val file = createExportFile("all_data", "json")

        val assetsList = assetRepository.getAllAssets().first()
        val liabilitiesList = liabilityRepository.getAllLiabilities().first()
        val cashFlowsList = cashFlowRepository.getAllCashFlows().first()

        val data = mapOf(
            "assets" to assetsList,
            "liabilities" to liabilitiesList,
            "cashFlows" to cashFlowsList,
            "exportDate" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        )

        FileWriter(file).use { writer ->
            writer.write(json.encodeToString(data))
        }
        file
    }

    private fun createExportFile(prefix: String, extension: String = "csv"): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val fileName = "${prefix}_$timeStamp.$extension"
        val exportDir = File(context.getExternalFilesDir(null), "exports")
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }
        return File(exportDir, fileName)
    }
}
