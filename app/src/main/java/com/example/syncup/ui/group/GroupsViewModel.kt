package com.example.syncup.ui.group

import com.example.syncup.data.repository.group.GroupsRepository
import kotlinx.coroutines.flow.*

class GroupsViewModel (val repo : GroupsRepository) {
    private val _uiState = MutableStateFlow(GroupsUiState())
    val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()

    init{
        loadGroups()
    }

    fun loadGroups(){
        _uiState.update {it.copy(groups = repo.getAll())}
    }

    fun addGroup(name: String){
        repo.create(name)
        loadGroups()
    }

    fun renameGroup(id: String, newName: String){
        repo.rename(id, newName)
        loadGroups()
    }

    fun deleteGroup(id: String) {
        repo.delete(id)
        loadGroups()
    }
}