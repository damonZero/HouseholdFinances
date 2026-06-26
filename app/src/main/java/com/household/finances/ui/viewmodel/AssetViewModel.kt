package com.household.finances.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.household.finances.data.model.Asset
import com.household.finances.data.model.AssetType
import com.household.finances.data.repository.AssetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AssetUiState(
    val assets: List<Asset> = emptyList(),
    val selectedType: AssetType? = null,
    val isLoading: Boolean = true,
    val showAddDialog: Boolean = false
)

@HiltViewModel
class AssetViewModel @Inject constructor(
    private val assetRepository: AssetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AssetUiState())
    val uiState: StateFlow<AssetUiState> = _uiState.asStateFlow()

    private val _selectedType = MutableStateFlow<AssetType?>(null)

    init {
        loadAssets()
    }

    private fun loadAssets() {
        _selectedType.flatMapLatest { type ->
            if (type != null) {
                assetRepository.getAssetsByType(type.name)
            } else {
                assetRepository.getAllAssets()
            }
        }.onEach { assets ->
            _uiState.update {
                it.copy(assets = assets, isLoading = false)
            }
        }.launchIn(viewModelScope)
    }

    fun selectType(type: AssetType?) {
        _selectedType.value = type
        _uiState.update { it.copy(selectedType = type) }
    }

    fun showAddDialog() {
        _uiState.update { it.copy(showAddDialog = true) }
    }

    fun hideAddDialog() {
        _uiState.update { it.copy(showAddDialog = false) }
    }

    fun addAsset(asset: Asset) {
        viewModelScope.launch {
            assetRepository.insertAsset(asset)
            hideAddDialog()
        }
    }

    fun updateAsset(asset: Asset) {
        viewModelScope.launch {
            assetRepository.updateAsset(asset)
        }
    }

    fun deleteAsset(asset: Asset) {
        viewModelScope.launch {
            assetRepository.deleteAsset(asset)
        }
    }
}
