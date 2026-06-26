package com.household.finances.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.household.finances.data.model.*
import com.household.finances.data.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class ReportUiState(
    val totalAssets: Double = 0.0,
    val totalLiabilities: Double = 0.0,
    val netWorth: Double = 0.0,
    val assetBreakdown: Map<AssetType, Double> = emptyMap(),
    val liabilityBreakdown: Map<LiabilityType, Double> = emptyMap(),
    val monthlyIncome: Double = 0.0,
    val monthlyExpense: Double = 0.0,
    val isLoading: Boolean = true
)

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val assetRepository: AssetRepository,
    private val liabilityRepository: LiabilityRepository,
    private val cashFlowRepository: CashFlowRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState())
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    init {
        loadReportData()
    }

    private fun loadReportData() {
        combine(
            assetRepository.getTotalAssets(),
            liabilityRepository.getTotalLiabilities()
        ) { totalAssets, totalLiabilities ->
            val assets = totalAssets ?: 0.0
            val liabilities = totalLiabilities ?: 0.0
            _uiState.update {
                it.copy(
                    totalAssets = assets,
                    totalLiabilities = liabilities,
                    netWorth = assets - liabilities,
                    isLoading = false
                )
            }
        }.launchIn(viewModelScope)

        // Load asset breakdown
        AssetType.entries.forEach { type ->
            assetRepository.getTotalAssetsByType(type.name).onEach { amount ->
                _uiState.update { state ->
                    state.copy(
                        assetBreakdown = state.assetBreakdown + (type to (amount ?: 0.0))
                    )
                }
            }.launchIn(viewModelScope)
        }

        // Load liability breakdown
        LiabilityType.entries.forEach { type ->
            liabilityRepository.getTotalLiabilitiesByType(type.name).onEach { amount ->
                _uiState.update { state ->
                    state.copy(
                        liabilityBreakdown = state.liabilityBreakdown + (type to (amount ?: 0.0))
                    )
                }
            }.launchIn(viewModelScope)
        }
    }
}
