package com.household.finances.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.household.finances.data.model.Insurance
import com.household.finances.data.model.InsuranceType
import com.household.finances.data.repository.InsuranceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InsuranceUiState(
    val insurances: List<Insurance> = emptyList(),
    val totalPremium: Double = 0.0,
    val totalCoverage: Double = 0.0,
    val selectedType: InsuranceType? = null,
    val isLoading: Boolean = true,
    val showAddDialog: Boolean = false
)

@HiltViewModel
class InsuranceViewModel @Inject constructor(
    private val insuranceRepository: InsuranceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InsuranceUiState())
    val uiState: StateFlow<InsuranceUiState> = _uiState.asStateFlow()

    init {
        loadInsurances()
    }

    private fun loadInsurances() {
        insuranceRepository.getAllInsurances().onEach { insurances ->
            val totalPremium = insurances.sumOf { it.annualPremium }
            val totalCoverage = insurances.sumOf { it.coverageAmount }
            _uiState.update {
                it.copy(
                    insurances = insurances,
                    totalPremium = totalPremium,
                    totalCoverage = totalCoverage,
                    isLoading = false
                )
            }
        }.launchIn(viewModelScope)
    }

    fun selectType(type: InsuranceType?) {
        _uiState.update { it.copy(selectedType = type) }
    }

    fun showAddDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }

    fun hideAddDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }

    fun addInsurance(insurance: Insurance) {
        viewModelScope.launch {
            insuranceRepository.insertInsurance(insurance)
            hideAddDialog()
        }
    }

    fun updateInsurance(insurance: Insurance) {
        viewModelScope.launch {
            insuranceRepository.updateInsurance(insurance)
        }
    }

    fun deleteInsurance(insurance: Insurance) {
        viewModelScope.launch {
            insuranceRepository.deleteInsurance(insurance)
        }
    }
}
