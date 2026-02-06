package com.example.syncup.ui.group.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncup.data.model.groups.Group
import com.example.syncup.data.repository.group.GroupsRepository
import com.example.syncup.ui.group.uistate.GroupsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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
@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val repo: GroupsRepository
) : ViewModel() {

    // Internal mutable state (only the ViewModel can update it)
    private val _uiState = MutableStateFlow(GroupsUiState())

    // Public read-only state observed by the UI (single source of truth)
    val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()

    val groups: StateFlow<List<Group>> =
        repo.observeAll()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    init {
        // Load initial data when the ViewModel is created
        viewModelScope.launch {
            repo.refresh()
        }
    }

    /**
     * Creates a new group and optionally invites emails.
     * Runs in viewModelScope to keep async work lifecycle-aware.
     */
    fun addGroup(name: String, invitedEmails: List<String> = emptyList()) {
        viewModelScope.launch {
            repo.create(name, invitedEmails)
        }
    }

    /**
     * Renames an existing group, then refreshes the UI state.
     */
    fun renameGroup(id: Long, newName: String) {
        viewModelScope.launch {
            repo.rename(id, newName)
        }
    }

    /**
     * Deletes a group by id, then refreshes the UI state.
     */
    fun deleteGroup(id: Long) {
        viewModelScope.launch {
            repo.delete(id)
        }
    }
}
