package com.example.syncup.ui.group.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncup.data.repository.group.GroupsRepository
import com.example.syncup.ui.group.uistate.GroupDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * GroupDetailViewModel
 *
 * ViewModel responsible for managing the UI state of GroupDetailScreen.
 * Holds the details of a specific group and exposes it as a StateFlow to the UI.
 *
 * Data operations are delegated to GroupsRepository.
 *
 * Note: Current implementation uses a simple "reload after change" strategy,
 * which is enough for the in-memory repository and MVP stage.
 */
class GroupDetailViewModel(private val repo : GroupsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(GroupDetailUiState())
    val uiState : StateFlow<GroupDetailUiState> = _uiState.asStateFlow()

    /**
     * Loads the details of a specific group from the repository and updates the UI state.
     * Runs in viewModelScope to keep async work lifecycle-aware.
     */
    init{
        loadGroups()
    }

    private fun loadGroups() {
        viewModelScope.launch {
            _uiState.update { current ->
                current.copy(groups = repo.getAll())
            }
        }
    }

    fun loadGroup(id : String) {
        viewModelScope.launch {
            _uiState.update { current ->
                val group = current.groups.find { it.id == id }
                current.copy(group = group)
            }
        }
    }
}