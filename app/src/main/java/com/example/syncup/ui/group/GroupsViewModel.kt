package com.example.syncup.ui.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncup.data.repository.group.GroupsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * GroupsViewModel
 *
 * ViewModel responsible for managing the UI state of GroupsScreen.
 * Holds the list of groups and exposes it as a StateFlow to the UI.
 *
 * Data operations are delegated to GroupsRepository.
 * After each mutation (create/rename/delete), the state is refreshed.
 *
 * Note: Current implementation uses a simple "reload after change" strategy,
 * which is enough for the in-memory repository and MVP stage.
 */
class GroupsViewModel(
    val repo: GroupsRepository
) : ViewModel() {

    // Internal mutable state (only the ViewModel can update it)
    private val _uiState = MutableStateFlow(GroupsUiState())

    // Public read-only state observed by the UI (single source of truth)
    val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()

    init {
        // Load initial data when the ViewModel is created
        loadGroups()
    }

    /**
     * Loads the current list of groups from the repository and updates the UI state.
     * Synchronous because the current repository implementation is local/in-memory.
     */
    fun loadGroups() {
        _uiState.update { current ->
            current.copy(groups = repo.getAll())
        }
    }

    /**
     * Creates a new group and optionally invites emails.
     * Runs in viewModelScope to keep async work lifecycle-aware.
     */
    fun addGroup(name: String, invitedEmails: List<String> = emptyList()) {
        viewModelScope.launch {
            repo.create(name, invitedEmails)
            loadGroups()
        }
    }

    /**
     * Renames an existing group, then refreshes the UI state.
     */
    fun renameGroup(id: String, newName: String) {
        viewModelScope.launch {
            repo.rename(id, newName)
            loadGroups()
        }
    }

    /**
     * Deletes a group by id, then refreshes the UI state.
     */
    fun deleteGroup(id: String) {
        viewModelScope.launch {
            repo.delete(id)
            loadGroups()
        }
    }
}
