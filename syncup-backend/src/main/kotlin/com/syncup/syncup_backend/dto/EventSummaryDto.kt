package com.syncup.syncup_backend.dto

import com.syncup.syncup_backend.model.EventStatus
import com.syncup.syncup_backend.model.TimeSlot

data class EventSummaryDto(
    val id: Long,
    val groupId: Long,
    val name: String,
    val description: String,
    val date: TimeSlot?,
    val status: EventStatus,
    val eventTypeId: Long?
)

