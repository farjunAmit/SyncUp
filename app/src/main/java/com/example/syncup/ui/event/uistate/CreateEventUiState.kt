package com.example.syncup.ui.event.uistate

import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventType
import com.example.syncup.data.model.events.SlotBlock
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.groups.Group

data class CreateEventUiState(
    val eventTypes: List<EventType> = emptyList(),
    val selectedEventType: EventType? = null,
    val group : Group? = null,
    val event : Event? = null,
    val slotsToBlock: Map<TimeSlot, SlotBlock> = emptyMap()
)