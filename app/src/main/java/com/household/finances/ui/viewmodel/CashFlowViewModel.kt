package com.household.finances.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.household.finances.data.model.CashFlow
import com.household.finances.data.model.CashFlowType
import com.household.finances.data.repository.CashFlowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class CashFlowUiState(
    val cashFlows: List<CashFlow> = emptyList(),
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val balance: Double = 0.0,
    val selectedMonth: Calendar = Calendar.getInstance(),
    val isLoading: Boolean = true,
    val showAddDialog: Boolean = false
)

@HiltViewModel
class CashFlowViewModel @Inject constructor(
    private val cashFlowRepository: CashFlowRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CashFlowUiState())
    val uiState: StateFlow<CashFlowUiState> = _uiState.asStateFlow()

    init {
        loadCashFlows()
    }

    private fun loadCashFlows() {
        val sourceCalendar = _uiState.value.selectedMonth
        // Create a copy to avoid mutating the state's Calendar
        val calendar = sourceCalendar.clone() as Calendar
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startDate = calendar.timeInMillis

        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.MILLISECOND, -1)
        val endDate = calendar.timeInMillis

        combine(
            cashFlowRepository.getCashFlowsByDateRange(startDate, endDate),
            cashFlowRepository.getTotalIncome(startDate, endDate),
            cashFlowRepository.getTotalExpense(startDate, endDate)
        ) { cashFlows, income, expense ->
            val totalIncome = income ?: 0.0
            val totalExpense = expense ?: 0.0
            _uiState.update {
                it.copy(
                    cashFlows = cashFlows,
                    totalIncome = totalIncome,
                    totalExpense = totalExpense,
                    balance = totalIncome - totalExpense,
                    isLoading = false
                )
            }
        }.launchIn(viewModelScope)
    }

    fun selectMonth(calendar: Calendar) {
        _uiState.update { it.copy(selectedMonth = calendar) }
        loadCashFlows()
    }

    fun previousMonth() {
        val calendar = _uiState.value.selectedMonth.clone() as Calendar
        calendar.add(Calendar.MONTH, -1)
        _uiState.update { it.copy(selectedMonth = calendar) }
        loadCashFlows()
    }

    fun nextMonth() {
        val calendar = _uiState.value.selectedMonth.clone() as Calendar
        calendar.add(Calendar.MONTH, 1)
        _uiState.update { it.copy(selectedMonth = calendar) }
        loadCashFlows()
    }

    fun showAddDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }

    fun hideAddDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }

    fun addCashFlow(cashFlow: CashFlow) {
        viewModelScope.launch {
            cashFlowRepository.insertCashFlow(cashFlow)
            hideAddDialog()
        }
    }

    fun deleteCashFlow(cashFlow: CashFlow) {
        viewModelScope.launch {
            cashFlowRepository.deleteCashFlow(cashFlow)
        }
    }
}
