package com.example.syncup.ui.group.uistate

import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventType
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.groups.Group
import java.time.LocalDate

/**
 * GroupDetailUiState
 *
 * Immutable UI state for the GroupDetail screen.
 * Represents all the data required to render the GroupDetailScreen.
 *
 * This state is exposed by GroupDetailViewModel and observed by the UI.
 */
data class GroupDetailUiState(
    val events: List<Event> = emptyList(),
    val scheduledEvents: Map<LocalDate, List<Event>> = emptyMap(),
    val eventTypes: Map<Long, EventType> = emptyMap(),
    val group : Group? = null
)
