package com.household.finances.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.household.finances.data.model.FinancialGoal
import com.household.finances.data.model.GoalStatus
import com.household.finances.data.model.GoalType
import com.household.finances.data.repository.FinancialGoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GoalUiState(
    val goals: List<FinancialGoal> = emptyList(),
    val selectedType: GoalType? = null,
    val isLoading: Boolean = true,
    val showAddDialog: Boolean = false
)

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val goalRepository: FinancialGoalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GoalUiState())
    val uiState: StateFlow<GoalUiState> = _uiState.asStateFlow()

    init {
        loadGoals()
    }

    private fun loadGoals() {
        goalRepository.getAllGoals().onEach { goals ->
            _uiState.update {
                it.copy(
                    goals = goals,
                    isLoading = false
                )
            }
        }.launchIn(viewModelScope)
    }

    fun selectType(type: GoalType?) {
        _uiState.update { it.copy(selectedType = type) }
    }

    fun showAddDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }

    fun hideAddDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }

    fun addGoal(goal: FinancialGoal) {
        viewModelScope.launch {
            goalRepository.insertGoal(goal)
            hideAddDialog()
        }
    }

    fun updateGoal(goal: FinancialGoal) {
        viewModelScope.launch {
            goalRepository.updateGoal(goal)
        }
    }

    fun deleteGoal(goal: FinancialGoal) {
        viewModelScope.launch {
            goalRepository.deleteGoal(goal)
        }
    }

    fun updateGoalProgress(goal: FinancialGoal, currentAmount: Double) {
        viewModelScope.launch {
            val updatedGoal = goal.copy(
                currentAmount = currentAmount,
                status = if (currentAmount >= goal.targetAmount) {
                    GoalStatus.COMPLETED
                } else {
                    goal.status
                }
            )
            goalRepository.updateGoal(updatedGoal)
        }
    }
}
