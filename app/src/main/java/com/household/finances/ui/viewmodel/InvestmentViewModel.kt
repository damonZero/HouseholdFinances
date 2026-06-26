package com.household.finances.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.household.finances.data.model.Investment
import com.household.finances.data.model.InvestmentType
import com.household.finances.data.repository.InvestmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InvestmentUiState(
    val investments: List<Investment> = emptyList(),
    val totalValue: Double = 0.0,
    val totalProfit: Double = 0.0,
    val selectedType: InvestmentType? = null,
    val isLoading: Boolean = true,
    val showAddDialog: Boolean = false
)

@HiltViewModel
class InvestmentViewModel @Inject constructor(
    private val investmentRepository: InvestmentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InvestmentUiState())
    val uiState: StateFlow<InvestmentUiState> = _uiState.asStateFlow()

    init {
        loadInvestments()
    }

    private fun loadInvestments() {
        investmentRepository.getAllInvestments().onEach { investments ->
            val totalValue = investments.sumOf { it.currentPrice * it.shares }
            val totalProfit = investments.sumOf {
                (it.currentPrice - it.purchasePrice) * it.shares
            }
            _uiState.update {
                it.copy(
                    investments = investments,
                    totalValue = totalValue,
                    totalProfit = totalProfit,
                    isLoading = false
                )
            }
        }.launchIn(viewModelScope)
    }

    fun selectType(type: InvestmentType?) {
        _uiState.update { it.copy(selectedType = type) }
    }

    fun showAddDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }

    fun hideAddDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }

    fun addInvestment(investment: Investment) {
        viewModelScope.launch {
            investmentRepository.insertInvestment(investment)
            hideAddDialog()
        }
    }

    fun updateInvestment(investment: Investment) {
        viewModelScope.launch {
            investmentRepository.updateInvestment(investment)
        }
    }

    fun deleteInvestment(investment: Investment) {
        viewModelScope.launch {
            investmentRepository.deleteInvestment(investment)
        }
    }
}
