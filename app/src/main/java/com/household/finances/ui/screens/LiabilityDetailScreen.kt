package com.household.finances.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.household.finances.data.model.Liability
import com.household.finances.ui.components.formatCurrency
import com.household.finances.ui.viewmodel.LiabilityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiabilityDetailScreen(
    liabilityId: Long,
    onBack: () -> Unit,
    viewModel: LiabilityViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val liability = uiState.liabilities.find { it.id == liabilityId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("负债详情") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    liability?.let {
                        IconButton(onClick = { viewModel.deleteLiability(it) }) {
                            Icon(Icons.Default.Delete, contentDescription = "删除")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (liability == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 负债概览卡片
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = liability.name,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = getLiabilityTypeName(liability.type),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "负债金额",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Text(
                            text = formatCurrency(liability.amount),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                // 还款信息卡片
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                            text = "还款信息",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        DetailRow("月供", formatCurrency(liability.monthlyPayment))
                        DetailRow("利率", "${liability.interestRate}%")
                        DetailRow("剩余期限", "${liability.remainingMonths}个月")

                        if (liability.monthlyPayment > 0 && liability.remainingMonths > 0) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                            Spacer(modifier = Modifier.height(12.dp))

                            val totalPayment = liability.monthlyPayment * liability.remainingMonths
                            val totalInterest = totalPayment - liability.amount

                            DetailRow("预计总还款", formatCurrency(totalPayment))
                            DetailRow("预计总利息", formatCurrency(totalInterest))
                        }
                    }
                }

                // 详细信息卡片
                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                            text = "详细信息",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        DetailRow("开始日期", formatDate(liability.startDate))
                        if (liability.description.isNotEmpty()) {
                            DetailRow("描述", liability.description)
                        }
                        if (liability.category.isNotEmpty()) {
                            DetailRow("分类", liability.category)
                        }
                        DetailRow("创建时间", formatDate(liability.createdAt))
                        DetailRow("更新时间", formatDate(liability.updatedAt))
                    }
                }

                // 标签
                if (liability.tags.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
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
                                text = "标签",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                liability.tags.forEach { tag ->
                                    SuggestionChip(
                                        onClick = { },
                                        label = { Text(tag) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
