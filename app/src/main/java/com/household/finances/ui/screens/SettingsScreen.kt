package com.household.finances.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.household.finances.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showExportMenu by remember { mutableStateOf(false) }
    var showImportMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("设置") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SettingsSection(title = "数据管理") {
                    // 导出数据
                    Box {
                        SettingsItem(
                            icon = Icons.Default.FileDownload,
                            title = "导出数据",
                            subtitle = if (uiState.isExporting) "导出中..." else "导出为CSV或JSON文件",
                            onClick = { showExportMenu = true }
                        )
                        DropdownMenu(
                            expanded = showExportMenu,
                            onDismissRequest = { showExportMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("导出资产") },
                                onClick = {
                                    viewModel.exportAssets()
                                    showExportMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("导出负债") },
                                onClick = {
                                    viewModel.exportLiabilities()
                                    showExportMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("导出现金流") },
                                onClick = {
                                    viewModel.exportCashFlows()
                                    showExportMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("导出全部数据") },
                                onClick = {
                                    viewModel.exportAllData()
                                    showExportMenu = false
                                }
                            )
                        }
                    }

                    // 导入数据
                    Box {
                        SettingsItem(
                            icon = Icons.Default.FileUpload,
                            title = "导入数据",
                            subtitle = if (uiState.isImporting) "导入中..." else "从CSV或JSON文件导入",
                            onClick = { showImportMenu = true }
                        )
                        DropdownMenu(
                            expanded = showImportMenu,
                            onDismissRequest = { showImportMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("导入资产") },
                                onClick = {
                                    // TODO: 打开文件选择器
                                    showImportMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("导入负债") },
                                onClick = {
                                    // TODO: 打开文件选择器
                                    showImportMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("导入全部数据") },
                                onClick = {
                                    // TODO: 打开文件选择器
                                    showImportMenu = false
                                }
                            )
                        }
                    }

                    SettingsItem(
                        icon = Icons.Default.Backup,
                        title = "备份恢复",
                        subtitle = "备份和恢复数据",
                        onClick = { }
                    )
                }
            }

            // 导出/导入消息
            uiState.exportMessage?.let { message ->
                item {
                    Snackbar(
                        action = {
                            TextButton(onClick = { viewModel.clearExportMessage() }) {
                                Text("关闭")
                            }
                        }
                    ) {
                        Text(message)
                    }
                }
            }

            uiState.importMessage?.let { message ->
                item {
                    Snackbar(
                        action = {
                            TextButton(onClick = { viewModel.clearImportMessage() }) {
                                Text("关闭")
                            }
                        }
                    ) {
                        Text(message)
                    }
                }
            }

            item {
                SettingsSection(title = "AI配置") {
                    SettingsItem(
                        icon = Icons.Default.Key,
                        title = "API Key设置",
                        subtitle = "设置AI服务的API密钥",
                        onClick = { }
                    )
                    SettingsItem(
                        icon = Icons.Default.SmartToy,
                        title = "模型选择",
                        subtitle = "选择AI模型",
                        onClick = { }
                    )
                }
            }

            item {
                SettingsSection(title = "个人偏好") {
                    SettingsItem(
                        icon = Icons.Default.Palette,
                        title = "主题设置",
                        subtitle = "深色/浅色主题",
                        onClick = { }
                    )
                    SettingsItem(
                        icon = Icons.Default.Notifications,
                        title = "通知设置",
                        subtitle = "管理通知偏好",
                        onClick = { }
                    )
                    SettingsItem(
                        icon = Icons.Default.Language,
                        title = "语言设置",
                        subtitle = "中文",
                        onClick = { }
                    )
                }
            }

            item {
                SettingsSection(title = "关于") {
                    SettingsItem(
                        icon = Icons.Default.Info,
                        title = "版本信息",
                        subtitle = "v1.0.0",
                        onClick = { }
                    )
                    SettingsItem(
                        icon = Icons.Default.Help,
                        title = "用户手册",
                        subtitle = "查看使用帮助",
                        onClick = { }
                    )
                    SettingsItem(
                        icon = Icons.Default.ContactMail,
                        title = "联系方式",
                        subtitle = "获取技术支持",
                        onClick = { }
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(text = title)
        },
        supportingContent = {
            Text(text = subtitle)
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = modifier.clickable(onClick = onClick)
    )
    Divider(color = MaterialTheme.colorScheme.surfaceVariant)
}
