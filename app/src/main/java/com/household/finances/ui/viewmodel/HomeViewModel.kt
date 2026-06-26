package com.household.finances.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.household.finances.data.model.AssetType
import com.household.finances.data.repository.AssetRepository
import com.household.finances.data.repository.LiabilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class HomeUiState(
    val totalAssets: Double = 0.0,
    val totalLiabilities: Double = 0.0,
    val netWorth: Double = 0.0,
    val assetBreakdown: Map<AssetType, Double> = emptyMap(),
    val isLoading: Boolean = true
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val assetRepository: AssetRepository,
    private val liabilityRepository: LiabilityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
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

        // Load asset breakdown by type
        AssetType.entries.forEach { type ->
            assetRepository.getTotalAssetsByType(type.name).onEach { amount ->
                _uiState.update { state ->
                    state.copy(
                        assetBreakdown = state.assetBreakdown + (type to (amount ?: 0.0))
                    )
                }
            }.launchIn(viewModelScope)
        }
    }
}
