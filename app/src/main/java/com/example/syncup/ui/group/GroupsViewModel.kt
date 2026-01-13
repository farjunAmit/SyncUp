package com.example.syncup.ui.group

import androidx.lifecycle.ViewModel
import com.example.syncup.data.repository.group.GroupsRepository
import kotlinx.coroutines.flow.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GroupsViewModel(val repo: GroupsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(GroupsUiState())
    val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()

    init {
        loadGroups()
    }

    fun loadGroups() {
        _uiState.update { it.copy(groups = repo.getAll()) }
    }

    fun addGroup(name: String, invitedEmails: List<String> = emptyList()) {
        viewModelScope.launch {
            repo.create(name, invitedEmails)
            loadGroups()
        }
    }

    fun renameGroup(id: String, newName: String) {
        viewModelScope.launch {
            repo.rename(id, newName)
            loadGroups()
        }
    }

    fun deleteGroup(id: String) {
        viewModelScope.launch {
            repo.delete(id)
            loadGroups()
        }
    }
}