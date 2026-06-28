package com.household.finances.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.household.finances.data.model.Investment
import com.household.finances.data.model.InvestmentType
import com.household.finances.ui.components.formatCurrency
import com.household.finances.ui.viewmodel.InvestmentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentScreen(
    viewModel: InvestmentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showFilterMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("投资") },
                actions = {
                    IconButton(onClick = { showFilterMenu = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "筛选")
                    }
                    DropdownMenu(
                        expanded = showFilterMenu,
                        onDismissRequest = { showFilterMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("全部") },
                            onClick = {
                                viewModel.selectType(null)
                                showFilterMenu = false
                            }
                        )
                        InvestmentType.entries.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(getInvestmentTypeName(type)) },
                                onClick = {
                                    viewModel.selectType(type)
                                    showFilterMenu = false
                                }
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.showAddDialog() }
            ) {
                Icon(Icons.Default.Add, contentDescription = "添加投资")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 投资概览
            InvestmentSummary(
                totalValue = uiState.totalValue,
                totalProfit = uiState.totalProfit
            )

            // 投资列表
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.investments.isEmpty()) {
                EmptyState(
                    message = "暂无投资记录",
                    icon = Icons.Default.TrendingUp
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.investments) { investment ->
                        InvestmentItem(
                            investment = investment,
                            onDelete = { viewModel.deleteInvestment(investment) }
                        )
                    }
                }
            }
        }

        if (uiState.showAddDialog) {
            AddInvestmentDialog(
                onDismiss = { viewModel.hideAddDialog() },
                onConfirm = { investment -> viewModel.addInvestment(investment) }
            )
        }
    }
}

@Composable
fun InvestmentSummary(
    totalValue: Double,
    totalProfit: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "投资总值",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = formatCurrency(totalValue),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "总收益: ${formatCurrency(totalProfit)}",
                style = MaterialTheme.typography.bodyMedium,
                color = if (totalProfit >= 0) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                }
            )
        }
    }
}

@Composable
fun InvestmentItem(
    investment: Investment,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalValue = investment.currentPrice * investment.shares
    val profit = (investment.currentPrice - investment.purchasePrice) * investment.shares
    val profitRate = if (investment.purchasePrice > 0) {
        ((investment.currentPrice - investment.purchasePrice) / investment.purchasePrice * 100)
    } else 0.0

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = investment.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = getInvestmentTypeName(investment.type),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (investment.symbol.isNotEmpty()) {
                        Text(
                            text = investment.symbol,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = formatCurrency(totalValue),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${if (profit >= 0) "+" else ""}${formatCurrency(profit)} (${String.format("%.2f", profitRate)}%)",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (profit >= 0) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailInfo("份额", "${investment.shares}")
                DetailInfo("买入价", formatCurrency(investment.purchasePrice))
                DetailInfo("当前价", formatCurrency(investment.currentPrice))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInvestmentDialog(
    onDismiss: () -> Unit,
    onConfirm: (Investment) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(InvestmentType.ETF) }
    var symbol by remember { mutableStateOf("") }
    var shares by remember { mutableStateOf("") }
    var purchasePrice by remember { mutableStateOf("") }
    var currentPrice by remember { mutableStateOf("") }
    var showTypeMenu by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("添加投资") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("投资名称") },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = showTypeMenu,
                    onExpandedChange = { showTypeMenu = it }
                ) {
                    OutlinedTextField(
                        value = getInvestmentTypeName(type),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("投资类型") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showTypeMenu) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = showTypeMenu,
                        onDismissRequest = { showTypeMenu = false }
                    ) {
                        InvestmentType.entries.forEach { investmentType ->
                            DropdownMenuItem(
                                text = { Text(getInvestmentTypeName(investmentType)) },
                                onClick = {
                                    type = investmentType
                                    showTypeMenu = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = symbol,
                    onValueChange = { symbol = it },
                    label = { Text("代码") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = shares,
                    onValueChange = { shares = it },
                    label = { Text("份额") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = purchasePrice,
                    onValueChange = { purchasePrice = it },
                    label = { Text("买入价") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = currentPrice,
                    onValueChange = { currentPrice = it },
                    label = { Text("当前价") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val investment = Investment(
                        name = name,
                        type = type,
                        symbol = symbol,
                        shares = shares.toDoubleOrNull() ?: 0.0,
                        purchasePrice = purchasePrice.toDoubleOrNull() ?: 0.0,
                        currentPrice = currentPrice.toDoubleOrNull() ?: 0.0
                    )
                    onConfirm(investment)
                }
            ) {
                Text("确认")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}

fun getInvestmentTypeName(type: InvestmentType): String {
    return when (type) {
        InvestmentType.ETF -> "ETF"
        InvestmentType.FUND -> "基金"
        InvestmentType.STOCK -> "股票"
        InvestmentType.BOND -> "债券"
        InvestmentType.OTHER -> "其他"
    }
}
