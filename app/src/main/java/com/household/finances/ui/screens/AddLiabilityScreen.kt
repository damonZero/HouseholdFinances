package com.household.finances.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.household.finances.data.model.Liability
import com.household.finances.data.model.LiabilityType
import com.household.finances.ui.viewmodel.LiabilityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLiabilityScreen(
    onBack: () -> Unit,
    viewModel: LiabilityViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(LiabilityType.MORTGAGE) }
    var amount by remember { mutableStateOf("") }
    var interestRate by remember { mutableStateOf("") }
    var monthlyPayment by remember { mutableStateOf("") }
    var remainingMonths by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showTypeMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("添加负债") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("负债名称 *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = showTypeMenu,
                onExpandedChange = { showTypeMenu = it }
            ) {
                OutlinedTextField(
                    value = getLiabilityTypeName(type),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("负债类型 *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showTypeMenu) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = showTypeMenu,
                    onDismissRequest = { showTypeMenu = false }
                ) {
                    LiabilityType.values().forEach { liabilityType ->
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
                label = { Text("负债金额 *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = interestRate,
                onValueChange = { interestRate = it },
                label = { Text("利率 (%)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = monthlyPayment,
                onValueChange = { monthlyPayment = it },
                label = { Text("月供") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = remainingMonths,
                onValueChange = { remainingMonths = it },
                label = { Text("剩余期限（月）") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("描述") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val liability = Liability(
                        name = name,
                        type = type,
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        interestRate = interestRate.toDoubleOrNull() ?: 0.0,
                        monthlyPayment = monthlyPayment.toDoubleOrNull() ?: 0.0,
                        remainingMonths = remainingMonths.toIntOrNull() ?: 0,
                        description = description
                    )
                    viewModel.addLiability(liability)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && amount.isNotBlank()
            ) {
                Text("保存")
            }
        }
    }
}
