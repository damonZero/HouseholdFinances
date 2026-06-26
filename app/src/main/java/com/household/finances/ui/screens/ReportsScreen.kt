package com.household.finances.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.household.finances.data.model.AssetType
import com.household.finances.data.model.LiabilityType
import com.household.finances.ui.components.*
import com.household.finances.ui.theme.*
import com.household.finances.ui.viewmodel.ReportViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    viewModel: ReportViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("报表") }
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "资产负债表",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                item {
                    NetWorthCard(
                        totalAssets = uiState.totalAssets,
                        totalLiabilities = uiState.totalLiabilities,
                        netWorth = uiState.netWorth
                    )
                }

                item {
                    Text(
                        text = "资产构成",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                item {
                    AssetBreakdownCard(
                        assetBreakdown = uiState.assetBreakdown,
                        totalAssets = uiState.totalAssets
                    )
                }

                item {
                    Text(
                        text = "负债构成",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                item {
                    LiabilityBreakdownCard(
                        liabilityBreakdown = uiState.liabilityBreakdown,
                        totalLiabilities = uiState.totalLiabilities
                    )
                }

                item {
                    Text(
                        text = "财务健康指标",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                item {
                    FinancialHealthCard(
                        assetLiabilityRatio = if (uiState.totalAssets > 0) {
                            uiState.totalLiabilities / uiState.totalAssets
                        } else 0.0,
                        savingsRate = 0.35
                    )
                }
            }
        }
    }
}

@Composable
fun AssetBreakdownCard(
    assetBreakdown: Map<AssetType, Double>,
    totalAssets: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            AssetType.values().forEach { type ->
                val amount = assetBreakdown[type] ?: 0.0
                val percentage = if (totalAssets > 0) {
                    (amount / totalAssets * 100).toInt()
                } else 0

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .padding(2.dp)
                        ) {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = getAssetTypeColor(type),
                                shape = MaterialTheme.shapes.small
                            ) {}
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = getAssetTypeName(type),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = formatCurrency(amount),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "$percentage%",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (type != AssetType.OTHER) {
                    Divider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun LiabilityBreakdownCard(
    liabilityBreakdown: Map<LiabilityType, Double>,
    totalLiabilities: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            LiabilityType.values().forEach { type ->
                val amount = liabilityBreakdown[type] ?: 0.0
                val percentage = if (totalLiabilities > 0) {
                    (amount / totalLiabilities * 100).toInt()
                } else 0

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .padding(2.dp)
                        ) {
                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = getLiabilityTypeColor(type),
                                shape = MaterialTheme.shapes.small
                            ) {}
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = getLiabilityTypeName(type),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = formatCurrency(amount),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "$percentage%",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (type != LiabilityType.OTHER) {
                    Divider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun getAssetTypeColor(type: AssetType): androidx.compose.ui.graphics.Color {
    return when (type) {
        AssetType.REAL_ESTATE -> AssetRealEstate
        AssetType.CASH -> AssetCash
        AssetType.INVESTMENT -> AssetInvestment
        AssetType.INSURANCE -> AssetInsurance
        AssetType.OTHER -> AssetOther
    }
}

@Composable
fun getLiabilityTypeColor(type: LiabilityType): androidx.compose.ui.graphics.Color {
    return when (type) {
        LiabilityType.MORTGAGE -> LiabilityMortgage
        LiabilityType.CAR_LOAN -> LiabilityCarLoan
        LiabilityType.CREDIT_CARD -> LiabilityCreditCard
        LiabilityType.CONSUMER_LOAN -> LiabilityConsumerLoan
        LiabilityType.OTHER -> LiabilityOther
    }
}

fun getLiabilityTypeName(type: LiabilityType): String {
    return when (type) {
        LiabilityType.MORTGAGE -> "房贷"
        LiabilityType.CAR_LOAN -> "车贷"
        LiabilityType.CREDIT_CARD -> "信用卡"
        LiabilityType.CONSUMER_LOAN -> "消费贷"
        LiabilityType.OTHER -> "其他"
    }
}
