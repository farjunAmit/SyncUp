package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.EventCreateRequestDto
import com.syncup.syncup_backend.dto.EventForVotingDto
import com.syncup.syncup_backend.dto.EventSummaryDto
import com.syncup.syncup_backend.dto.EventTypeCreateRequestDto
import com.syncup.syncup_backend.dto.EventTypeDto
import com.syncup.syncup_backend.dto.SlotVotingSummaryDto
import com.syncup.syncup_backend.dto.TimeSlotDto
import com.syncup.syncup_backend.entity.EventEntity
import com.syncup.syncup_backend.entity.EventTypeEntity
import com.syncup.syncup_backend.entity.GroupEntity
import com.syncup.syncup_backend.model.EventStatus
import com.syncup.syncup_backend.model.TimeSlot

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

fun EventCreateRequestDto.toEventEntity(groupId : Long): EventEntity {
    return EventEntity(
        name = this.name,
        description = this.description,
        groupId = groupId,
        eventTypeId = this.eventTypeId,
        date = null,
        status = EventStatus.VOTING,
        decisionMode = this.decisionMode
    )
}

fun TimeSlot.toDto(): TimeSlotDto {
    return TimeSlotDto(
        date = this.date,
        partOfDay = this.partOfDay
    )
}

fun TimeSlotDto.toModel(): TimeSlot {
    return TimeSlot(
        date = this.date,
        partOfDay = this.partOfDay
    )
}

fun EventTypeEntity.toEventTypeDto() : EventTypeDto{
    return EventTypeDto(
        id = this.id,
        type = this.name,
        groupId = this.group!!.id,
        color = this.color
    )
}

fun EventTypeCreateRequestDto.toEventTypeEntity(groupEntity: GroupEntity) : EventTypeEntity{
    return EventTypeEntity(
        group = groupEntity,
        name = this.type,
        color = this.color,
    )
}

