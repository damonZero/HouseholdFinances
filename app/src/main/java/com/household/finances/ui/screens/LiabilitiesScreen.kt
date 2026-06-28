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
import com.household.finances.data.model.Liability
import com.household.finances.data.model.LiabilityType
import com.household.finances.ui.components.formatCurrency
import com.household.finances.ui.theme.*
import com.household.finances.ui.viewmodel.LiabilityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiabilitiesScreen(
    viewModel: LiabilityViewModel = hiltViewModel(),
    onLiabilityClick: (Long) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var showFilterMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("负债") },
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
                        LiabilityType.entries.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(getLiabilityTypeName(type)) },
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
                Icon(Icons.Default.Add, contentDescription = "添加负债")
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.liabilities.isEmpty()) {
            EmptyState(
                message = "暂无负债",
                icon = Icons.Default.CreditCard
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.liabilities) { liability ->
                    LiabilityItem(
                        liability = liability,
                        onClick = { onLiabilityClick(liability.id) },
                        onDelete = { viewModel.deleteLiability(liability) }
                    )
                }
            }
        }

        if (uiState.showAddDialog) {
            AddLiabilityDialog(
                onDismiss = { viewModel.hideAddDialog() },
                onConfirm = { liability -> viewModel.addLiability(liability) }
            )
        }
    }
}

@Composable
fun LiabilityItem(
    liability: Liability,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = liability.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = getLiabilityTypeName(liability.type),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = formatCurrency(liability.amount),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "删除",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            if (liability.monthlyPayment > 0 || liability.interestRate > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (liability.monthlyPayment > 0) {
                        DetailInfo("月供", formatCurrency(liability.monthlyPayment))
                    }
                    if (liability.interestRate > 0) {
                        DetailInfo("利率", "${liability.interestRate}%")
                    }
                    if (liability.remainingMonths > 0) {
                        DetailInfo("剩余", "${liability.remainingMonths}个月")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailInfo(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLiabilityDialog(
    onDismiss: () -> Unit,
    onConfirm: (Liability) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(LiabilityType.MORTGAGE) }
    var amount by remember { mutableStateOf("") }
    var interestRate by remember { mutableStateOf("") }
    var monthlyPayment by remember { mutableStateOf("") }
    var remainingMonths by remember { mutableStateOf("") }
    var showTypeMenu by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("添加负债") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("负债名称") },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = showTypeMenu,
                    onExpandedChange = { showTypeMenu = it }
                ) {
                    OutlinedTextField(
                        value = getLiabilityTypeName(type),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("负债类型") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showTypeMenu) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = showTypeMenu,
                        onDismissRequest = { showTypeMenu = false }
                    ) {
                        LiabilityType.entries.forEach { liabilityType ->
                            DropdownMenuItem(
                                text = { Text(getLiabilityTypeName(liabilityType)) },
                                onClick = {
                                    type = liabilityType
                                    showTypeMenu = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("负债金额") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = interestRate,
                    onValueChange = { interestRate = it },
                    label = { Text("利率 (%)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = monthlyPayment,
                    onValueChange = { monthlyPayment = it },
                    label = { Text("月供") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = remainingMonths,
                    onValueChange = { remainingMonths = it },
                    label = { Text("剩余期限（月）") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val liability = Liability(
                        name = name,
                        type = type,
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        interestRate = interestRate.toDoubleOrNull() ?: 0.0,
                        monthlyPayment = monthlyPayment.toDoubleOrNull() ?: 0.0,
                        remainingMonths = remainingMonths.toIntOrNull() ?: 0
                    )
                    onConfirm(liability)
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
