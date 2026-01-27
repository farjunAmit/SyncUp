package com.example.syncup.data.dto

import com.example.syncup.data.model.events.EventStatus
import com.example.syncup.data.model.events.TimeSlot

data class EventSummaryDto(
    val id: Long,
    val groupId: Long,
    val name: String,
    val description: String,
    val date: TimeSlot?,
    val status: EventStatus,
    val eventTypeId: Long?
)