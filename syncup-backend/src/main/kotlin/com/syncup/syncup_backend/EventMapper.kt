package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.EventForVotingDto
import com.syncup.syncup_backend.dto.EventSummaryDto
import com.syncup.syncup_backend.dto.SlotVotingSummaryDto
import com.syncup.syncup_backend.entity.EventEntity
import com.syncup.syncup_backend.entity.EventPossibleSlotEntity

fun EventEntity.toEventDto(): EventSummaryDto {
    return EventSummaryDto(
        id = this.id,
        name = this.name,
        description = this.description,
        date = this.date,
        groupId = this.groupId,
        status = this.status,
        eventTypeId = this.eventTypeId
    )
}

fun EventEntity.toEventDto(slots: List<SlotVotingSummaryDto>) : EventForVotingDto{
    return EventForVotingDto(
        id = this.id,
        name = this.name,
        description = this.description,
        groupId = this.groupId,
        eventTypeId = this.eventTypeId,
        slots = slots
    )
}
