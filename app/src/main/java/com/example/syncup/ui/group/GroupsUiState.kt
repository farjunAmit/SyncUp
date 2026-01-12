package com.example.syncup.ui.group

import com.example.syncup.data.model.Group

data class GroupsUiState(
    val groups: List<Group> = emptyList(),
)