package com.syncup.syncup_backend.dto

import com.syncup.syncup_backend.model.EventStatus

data class EventDetailDto(
    val id: Long,
    val groupId: Long,
    val name: String,
    val description: String,
    val eventTypeId: Long?,
    val eventStatus: EventStatus,
    val slots: List<SlotVotingSummaryDto>
)