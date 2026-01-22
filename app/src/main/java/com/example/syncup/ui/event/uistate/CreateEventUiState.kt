package com.example.syncup.ui.event.uistate

import com.example.syncup.data.model.events.EventType
import com.example.syncup.data.model.groups.Group

data class CreateEventUiState(
    val eventTypes: List<EventType> = emptyList(),
    val selectedEventType: EventType? = null,
    val group : Group? = null
)