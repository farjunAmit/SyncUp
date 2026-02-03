package com.example.syncup.data.dto

import com.example.syncup.data.model.events.EventStatus

data class EventDetailDto(
    val id: Long,
    val groupId: Long,
    val name: String,
    val description: String,
    val eventTypeId: Long?,
    val eventStatus: EventStatus,
    val slots: List<SlotVotingSummaryDto>
)
