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
import com.household.finances.data.model.Asset
import com.household.finances.data.model.AssetType
import com.household.finances.ui.viewmodel.AssetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssetScreen(
    onBack: () -> Unit,
    viewModel: AssetViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(AssetType.CASH) }
    var value by remember { mutableStateOf("") }
    var purchasePrice by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var showTypeMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("添加资产") },
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
                label = { Text("资产名称 *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = showTypeMenu,
                onExpandedChange = { showTypeMenu = it }
            ) {
                OutlinedTextField(
                    value = getAssetTypeName(type),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("资产类型 *") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showTypeMenu) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = showTypeMenu,
                    onDismissRequest = { showTypeMenu = false }
                ) {
                    AssetType.entries.forEach { assetType ->
                        DropdownMenuItem(
                            text = { Text(getAssetTypeName(assetType)) },
                            onClick = {
                                type = assetType
                                showTypeMenu = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                label = { Text("当前价值 *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = purchasePrice,
                onValueChange = { purchasePrice = it },
                label = { Text("购入价格") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("分类") },
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
                    val asset = Asset(
                        name = name,
                        type = type,
                        value = value.toDoubleOrNull() ?: 0.0,
                        purchasePrice = purchasePrice.toDoubleOrNull() ?: 0.0,
                        description = description,
                        category = category
                    )
                    viewModel.addAsset(asset)
                    onBack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && value.isNotBlank()
            ) {
                Text("保存")
            }
        }
    }
}
