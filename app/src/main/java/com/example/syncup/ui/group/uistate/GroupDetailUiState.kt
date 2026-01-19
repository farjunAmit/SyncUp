package com.example.syncup.ui.group.uistate

import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.groups.Group

/**
 * GroupDetailUiState
 *
 * Immutable UI state for the GroupDetail screen.
 * Represents all the data required to render the GroupDetailScreen.
 *
 * This state is exposed by GroupDetailViewModel and observed by the UI.
 */
data class GroupDetailUiState(
    val groups: List<Group> = emptyList(),
    val events: List<Event> = emptyList(),
    val group : Group? = null
)
