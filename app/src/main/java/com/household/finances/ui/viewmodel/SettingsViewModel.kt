package com.household.finances.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.household.finances.data.repository.DataExportRepository
import com.household.finances.data.repository.DataImportRepository
import com.household.finances.data.repository.ImportResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class SettingsUiState(
    val isExporting: Boolean = false,
    val isImporting: Boolean = false,
    val exportMessage: String? = null,
    val importMessage: String? = null,
    val showExportDialog: Boolean = false,
    val showImportDialog: Boolean = false
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val exportRepository: DataExportRepository,
    private val importRepository: DataImportRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun showExportDialog() {
        _uiState.update { it.copy(showExportDialog = true) }
    }

    fun hideExportDialog() {
        _uiState.update { it.copy(showExportDialog = false) }
    }

    fun showImportDialog() {
        _uiState.update { it.copy(showImportDialog = true) }
    }

    fun hideImportDialog() {
        _uiState.update { it.copy(showImportDialog = false) }
    }

    fun exportAssets() {
        viewModelScope.launch {
            _uiState.update { it.copy(isExporting = true, exportMessage = null) }
            try {
                val file = exportRepository.exportAssetsToCsv()
                _uiState.update {
                    it.copy(
                        isExporting = false,
                        exportMessage = "资产导出成功: ${file.absolutePath}"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isExporting = false,
                        exportMessage = "导出失败: ${e.message}"
                    )
                }
            }
        }
    }

    fun exportLiabilities() {
        viewModelScope.launch {
            _uiState.update { it.copy(isExporting = true, exportMessage = null) }
            try {
                val file = exportRepository.exportLiabilitiesToCsv()
                _uiState.update {
                    it.copy(
                        isExporting = false,
                        exportMessage = "负债导出成功: ${file.absolutePath}"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isExporting = false,
                        exportMessage = "导出失败: ${e.message}"
                    )
                }
            }
        }
    }

    fun exportCashFlows() {
        viewModelScope.launch {
            _uiState.update { it.copy(isExporting = true, exportMessage = null) }
            try {
                val file = exportRepository.exportCashFlowsToCsv()
                _uiState.update {
                    it.copy(
                        isExporting = false,
                        exportMessage = "现金流导出成功: ${file.absolutePath}"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isExporting = false,
                        exportMessage = "导出失败: ${e.message}"
                    )
                }
            }
        }
    }

    fun exportAllData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isExporting = true, exportMessage = null) }
            try {
                val file = exportRepository.exportAllDataToJson()
                _uiState.update {
                    it.copy(
                        isExporting = false,
                        exportMessage = "全部数据导出成功: ${file.absolutePath}"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isExporting = false,
                        exportMessage = "导出失败: ${e.message}"
                    )
                }
            }
        }
    }

    fun importAssets(file: File) {
        viewModelScope.launch {
            _uiState.update { it.copy(isImporting = true, importMessage = null) }
            try {
                val result = importRepository.importAssetsFromCsv(file)
                _uiState.update {
                    it.copy(
                        isImporting = false,
                        importMessage = result.message
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isImporting = false,
                        importMessage = "导入失败: ${e.message}"
                    )
                }
            }
        }
    }

    fun importLiabilities(file: File) {
        viewModelScope.launch {
            _uiState.update { it.copy(isImporting = true, importMessage = null) }
            try {
                val result = importRepository.importLiabilitiesFromCsv(file)
                _uiState.update {
                    it.copy(
                        isImporting = false,
                        importMessage = result.message
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isImporting = false,
                        importMessage = "导入失败: ${e.message}"
                    )
                }
            }
        }
    }

    fun importAllData(file: File) {
        viewModelScope.launch {
            _uiState.update { it.copy(isImporting = true, importMessage = null) }
            try {
                val result = importRepository.importAllDataFromJson(file)
                _uiState.update {
                    it.copy(
                        isImporting = false,
                        importMessage = result.message
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isImporting = false,
                        importMessage = "导入失败: ${e.message}"
                    )
                }
            }
        }
    }

    fun clearExportMessage() {
        _uiState.update { it.copy(exportMessage = null) }
    }

    fun clearImportMessage() {
        _uiState.update { it.copy(importMessage = null) }
    }
}
