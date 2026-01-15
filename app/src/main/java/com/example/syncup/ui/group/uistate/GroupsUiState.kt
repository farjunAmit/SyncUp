package com.example.syncup.ui.group.uistate

import com.example.syncup.data.model.Group

/**
 * GroupsUiState
 *
 * Immutable UI state for the Groups screen.
 * Represents all the data required to render the GroupsScreen.
 *
 * This state is exposed by GroupsViewModel and observed by the UI.
 */
data class GroupsUiState(
    val groups: List<Group> = emptyList(),
)
