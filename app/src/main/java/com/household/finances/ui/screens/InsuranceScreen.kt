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
import com.household.finances.data.model.Insurance
import com.household.finances.data.model.InsuranceType
import com.household.finances.ui.components.formatCurrency
import com.household.finances.ui.viewmodel.InsuranceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsuranceScreen(
    viewModel: InsuranceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showFilterMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("保险") },
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
                        InsuranceType.values().forEach { type ->
                            DropdownMenuItem(
                                text = { Text(getInsuranceTypeName(type)) },
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
                Icon(Icons.Default.Add, contentDescription = "添加保险")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 保险概览
            InsuranceSummary(
                totalPremium = uiState.totalPremium,
                totalCoverage = uiState.totalCoverage
            )

            // 保险列表
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.insurances.isEmpty()) {
                EmptyState(
                    message = "暂无保险记录",
                    icon = Icons.Default.Security
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.insurances) { insurance ->
                        InsuranceItem(
                            insurance = insurance,
                            onDelete = { viewModel.deleteInsurance(insurance) }
                        )
                    }
                }
            }
        }

        if (uiState.showAddDialog) {
            AddInsuranceDialog(
                onDismiss = { viewModel.hideAddDialog() },
                onConfirm = { insurance -> viewModel.addInsurance(insurance) }
            )
        }
    }
}

@Composable
fun InsuranceSummary(
    totalPremium: Double,
    totalCoverage: Double,
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
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "年保费",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = formatCurrency(totalPremium),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "总保额",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = formatCurrency(totalCoverage),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    }
}

@Composable
fun InsuranceItem(
    insurance: Insurance,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                        text = insurance.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = insurance.company,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = getInsuranceTypeName(insurance.type),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

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

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailInfo("保额", formatCurrency(insurance.coverageAmount))
                DetailInfo("年保费", formatCurrency(insurance.annualPremium))
                DetailInfo("交费年限", "${insurance.paymentYears}年")
            }

            if (insurance.insuredPerson.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "被保险人: ${insurance.insuredPerson}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInsuranceDialog(
    onDismiss: () -> Unit,
    onConfirm: (Insurance) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(InsuranceType.LIFE) }
    var coverageAmount by remember { mutableStateOf("") }
    var annualPremium by remember { mutableStateOf("") }
    var paymentYears by remember { mutableStateOf("") }
    var insuredPerson by remember { mutableStateOf("") }
    var showTypeMenu by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("添加保险") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("保险名称") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = company,
                    onValueChange = { company = it },
                    label = { Text("保险公司") },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = showTypeMenu,
                    onExpandedChange = { showTypeMenu = it }
                ) {
                    OutlinedTextField(
                        value = getInsuranceTypeName(type),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("保险类型") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showTypeMenu) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = showTypeMenu,
                        onDismissRequest = { showTypeMenu = false }
                    ) {
                        InsuranceType.values().forEach { insuranceType ->
                            DropdownMenuItem(
                                text = { Text(getInsuranceTypeName(insuranceType)) },
                                onClick = {
                                    type = insuranceType
                                    showTypeMenu = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = coverageAmount,
                    onValueChange = { coverageAmount = it },
                    label = { Text("保额") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = annualPremium,
                    onValueChange = { annualPremium = it },
                    label = { Text("年保费") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = paymentYears,
                    onValueChange = { paymentYears = it },
                    label = { Text("交费年限") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = insuredPerson,
                    onValueChange = { insuredPerson = it },
                    label = { Text("被保险人") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val insurance = Insurance(
                        name = name,
                        company = company,
                        type = type,
                        coverageAmount = coverageAmount.toDoubleOrNull() ?: 0.0,
                        annualPremium = annualPremium.toDoubleOrNull() ?: 0.0,
                        paymentYears = paymentYears.toIntOrNull() ?: 0,
                        insuredPerson = insuredPerson
                    )
                    onConfirm(insurance)
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

fun getInsuranceTypeName(type: InsuranceType): String {
    return when (type) {
        InsuranceType.LIFE -> "寿险"
        InsuranceType.HEALTH -> "健康险"
        InsuranceType.ACCIDENT -> "意外险"
        InsuranceType.INVESTMENT -> "理财险"
        InsuranceType.OTHER -> "其他"
    }
}
