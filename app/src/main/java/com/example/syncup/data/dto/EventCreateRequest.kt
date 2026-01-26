package com.example.syncup.data.dto

import com.example.syncup.data.model.events.DecisionMode

data class EventCreateRequestDto(
    val groupId: Long,
    val name: String,
    val description: String,
    val possibleSlots: List<TimeSlotDto>,
    val decisionMode: DecisionMode,
    val eventTypeId : Long? = null,
)
