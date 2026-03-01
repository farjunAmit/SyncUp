package com.example.syncup.data.mapper

import com.example.syncup.data.dto.EventDetailDto
import com.example.syncup.data.dto.EventSummaryDto
import com.example.syncup.data.dto.EventTypeDto
import com.example.syncup.data.dto.TimeSlotDto
import com.example.syncup.data.local.EventEntity
import com.example.syncup.data.local.EventTypeEntity
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventType
import com.example.syncup.data.model.events.PartOfDay
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

private val gson = Gson()

fun EventSummaryDto.toEvent() : Event {
    val event = Event(
        id = id,
        groupId = groupId,
        title = name,
        possibleSlots = emptySet(),
        description = description,
        eventTypeId = eventTypeId
    )
    event.setEventStatus(status)
    if(date != null){
        event.setFinalDate(date.toTimeSlot())
    }
    return event
}

fun EventDetailDto.toEvent() : Event {
    val votes = this.slots
    val possibleSlots = votes.map { it.timeSlot.toTimeSlot() }.toSet()
    val myVotes = votes.associate { it.timeSlot.toTimeSlot() to it.myVote }
    val slotCounts = votes.associate { it.timeSlot.toTimeSlot() to it.votes }
    val status = this.eventStatus
    val event = Event(
        id = id,
        groupId = groupId,
        title = name,
        possibleSlots = possibleSlots,
        description = description,
        eventTypeId = eventTypeId
    )
    event.setEventStatus(status)
    event.myVotes = myVotes
    event.slotCounts = slotCounts
    return event
}

fun Event.toEntity(): EventEntity {
    return EventEntity(
        id = id,
        groupId = groupId,
        title = title,
        description = description,
        status = eventStatus,
        decisionMode = decisionMode,
        eventTypeId = eventTypeId,
        possibleSlotsJson = gson.toJson(possibleSlots),
        myVotesJson = gson.toJson(myVotes),
        slotCountsJson = gson.toJson(slotCounts),
        finalDateJson = gson.toJson(finalDate)
    )
}

fun EventEntity.toEvent(): Event {
    val possibleSlotsType = object : TypeToken<Set<TimeSlot>>() {}.type
    val myVotesType = object : TypeToken<Map<TimeSlot, Vote?>>() {}.type
    val slotCountsType = object : TypeToken<Map<TimeSlot, Map<Vote, Int>>>() {}.type
    val finalDateType = object : TypeToken<TimeSlot?>() {}.type

    val event = Event(
        id = id,
        groupId = groupId,
        title = title,
        possibleSlots = gson.fromJson(possibleSlotsJson, possibleSlotsType),
        description = description,
        decisionMode = decisionMode,
        eventTypeId = eventTypeId
    )
    event.setEventStatus(status)
    event.myVotes = gson.fromJson(myVotesJson, myVotesType)
    event.slotCounts = gson.fromJson(slotCountsJson, slotCountsType)
    event.setFinalDate(gson.fromJson(finalDateJson, finalDateType))
    return event
}

fun EventType.toEntity(): EventTypeEntity {
    return EventTypeEntity(
        id = id,
        groupId = groupId,
        type = name,
        color = color
    )
}

fun EventTypeEntity.toEventType(): EventType {
    return EventType(
        id = id,
        name = type,
        color = color,
        groupId = groupId
    )
}

fun TimeSlotDto.toTimeSlot() : TimeSlot {
    return TimeSlot(
        date = date,
        partOfDay = partOfDay
    )
}

fun TimeSlot.toTimeSlotDto() : TimeSlotDto {
    return TimeSlotDto(
        date = date,
        partOfDay = partOfDay
    )
}

fun EventTypeDto.toEventType() : EventType {
    return EventType(
        id = id,
        name = this.type,
        color = color,
        groupId = groupId
    )
}