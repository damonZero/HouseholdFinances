package com.household.finances.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.household.finances.data.model.Liability
import com.household.finances.data.model.LiabilityType
import com.household.finances.data.repository.LiabilityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LiabilityUiState(
    val liabilities: List<Liability> = emptyList(),
    val selectedType: LiabilityType? = null,
    val isLoading: Boolean = true,
    val showAddDialog: Boolean = false
)

@HiltViewModel
class LiabilityViewModel @Inject constructor(
    private val liabilityRepository: LiabilityRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LiabilityUiState())
    val uiState: StateFlow<LiabilityUiState> = _uiState.asStateFlow()

    private val _selectedType = MutableStateFlow<LiabilityType?>(null)

    init {
        loadLiabilities()
    }

    private fun loadLiabilities() {
        _selectedType.flatMapLatest { type ->
            if (type != null) {
                liabilityRepository.getLiabilitiesByType(type.name)
            } else {
                liabilityRepository.getAllLiabilities()
            }
        }.onEach { liabilities ->
            _uiState.update {
                it.copy(liabilities = liabilities, isLoading = false)
            }
        }.launchIn(viewModelScope)
    }

    fun selectType(type: LiabilityType?) {
        _selectedType.value = type
        _uiState.update { it.copy(selectedType = type) }
    }

    fun showAddDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }

    fun hideAddDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }

    fun addLiability(liability: Liability) {
        viewModelScope.launch {
            liabilityRepository.insertLiability(liability)
            hideAddDialog()
        }
    }

    fun updateLiability(liability: Liability) {
        viewModelScope.launch {
            liabilityRepository.updateLiability(liability)
        }
    }

    fun deleteLiability(liability: Liability) {
        viewModelScope.launch {
            liabilityRepository.deleteLiability(liability)
        }
    }
}
