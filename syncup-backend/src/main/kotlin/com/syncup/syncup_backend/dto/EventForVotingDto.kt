package com.syncup.syncup_backend.dto

import com.syncup.syncup_backend.model.TimeSlot
import com.syncup.syncup_backend.model.Vote

data class EventForVotingDto(
    val id: Long,
    val groupId: Long,
    val name: String,
    val description: String,
    val eventTypeId: Long?,
    val slots: List<SlotVotingSummaryDto>
)