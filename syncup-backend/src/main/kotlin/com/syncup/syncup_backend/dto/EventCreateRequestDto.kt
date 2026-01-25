package com.syncup.syncup_backend.dto

import com.syncup.syncup_backend.model.DecisionMode


data class EventCreateRequestDto(
    val groupId: Long,
    val name: String,
    val description: String,
    val possibleSlots: List<TimeSlotDto>,
    val decisionMode: DecisionMode,
    val eventTypeId : Long? = null,
)