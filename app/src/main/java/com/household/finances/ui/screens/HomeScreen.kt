package com.household.finances.ui.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.household.finances.data.model.AssetType
import com.household.finances.ui.components.*
import com.household.finances.ui.theme.*
import com.household.finances.ui.navigation.Screen
import com.household.finances.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            NetWorthCard(
                totalAssets = uiState.totalAssets,
                totalLiabilities = uiState.totalLiabilities,
                netWorth = uiState.netWorth
            )
        }

        item {
            Text(
                text = "资产配置",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FinancialCard(
                    title = "房产",
                    amount = uiState.assetBreakdown[AssetType.REAL_ESTATE] ?: 0.0,
                    icon = Icons.Default.Home,
                    color = AssetRealEstate,
                    modifier = Modifier.weight(1f)
                )

                FinancialCard(
                    title = "现金",
                    amount = uiState.assetBreakdown[AssetType.CASH] ?: 0.0,
                    icon = Icons.Default.AccountBalanceWallet,
                    color = AssetCash,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FinancialCard(
                    title = "投资",
                    amount = uiState.assetBreakdown[AssetType.INVESTMENT] ?: 0.0,
                    icon = Icons.Default.TrendingUp,
                    color = AssetInvestment,
                    modifier = Modifier.weight(1f)
                )

                FinancialCard(
                    title = "保险",
                    amount = uiState.assetBreakdown[AssetType.INSURANCE] ?: 0.0,
                    icon = Icons.Default.Security,
                    color = AssetInsurance,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            FinancialCard(
                title = "其他资产",
                amount = uiState.assetBreakdown[AssetType.OTHER] ?: 0.0,
                icon = Icons.Default.MoreHoriz,
                color = AssetOther
            )
        }

        item {
            Text(
                text = "财务健康度",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        item {
            FinancialHealthCard(
                assetLiabilityRatio = if (uiState.totalAssets > 0) {
                    uiState.totalLiabilities / uiState.totalAssets
                } else 0.0,
                savingsRate = 0.35 // 示例值
            )
        }

        item {
            Text(
                text = "快捷操作",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        item {
            QuickActionsCard(onNavigate = onNavigate)
        }

        item {
            Text(
                text = "资产配置图",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        item {
            AssetAllocationChart(assetBreakdown = uiState.assetBreakdown)
        }
    }
}

@Composable
fun QuickActionsCard(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            QuickActionButton(
                icon = Icons.Default.Add,
                label = "添加资产",
                onClick = { onNavigate(Screen.AddAsset.route) }
            )
            QuickActionButton(
                icon = Icons.Default.CreditCard,
                label = "添加负债",
                onClick = { onNavigate(Screen.AddLiability.route) }
            )
            QuickActionButton(
                icon = Icons.Default.Receipt,
                label = "记录收支",
                onClick = { onNavigate(Screen.CashFlow.route) }
            )
            QuickActionButton(
                icon = Icons.Default.TrendingUp,
                label = "投资管理",
                onClick = { onNavigate(Screen.Investments.route) }
            )
        }
    }
}

@Composable
fun QuickActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilledIconButton(
            onClick = onClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun AssetAllocationChart(
    assetBreakdown: Map<AssetType, Double>,
    modifier: Modifier = Modifier
) {
    val chartData = assetBreakdown
        .filter { it.value > 0 }
        .mapKeys { getAssetTypeName(it.key) }

    val colors = listOf(
        AssetRealEstate,
        AssetCash,
        AssetInvestment,
        AssetInsurance,
        AssetOther
    )

    if (chartData.isNotEmpty()) {
        DonutChart(
            data = chartData,
            colors = colors,
            modifier = modifier,
            title = "资产配置"
        )
    } else {
        Card(
            modifier = modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "暂无资产数据",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun FinancialHealthCard(
    assetLiabilityRatio: Double,
    savingsRate: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "财务指标",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            HealthIndicator(
                label = "资产负债率",
                value = assetLiabilityRatio,
                threshold = 0.5,
                isLowerBetter = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            HealthIndicator(
                label = "储蓄率",
                value = savingsRate,
                threshold = 0.3,
                isLowerBetter = false
            )
        }
    }
}

@Composable
fun HealthIndicator(
    label: String,
    value: Double,
    threshold: Double,
    isLowerBetter: Boolean,
    modifier: Modifier = Modifier
) {
    val isHealthy = if (isLowerBetter) {
        value < threshold
    } else {
        value > threshold
    }

    val color = if (isHealthy) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.error
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "${(value * 100).toInt()}%",
            style = MaterialTheme.typography.bodyMedium,
            color = color
        )
    }

    Spacer(modifier = Modifier.height(4.dp))

    LinearProgressIndicator(
        progress = value.toFloat(),
        modifier = Modifier.fillMaxWidth(),
        color = color,
        trackColor = MaterialTheme.colorScheme.surfaceVariant
    )
}
