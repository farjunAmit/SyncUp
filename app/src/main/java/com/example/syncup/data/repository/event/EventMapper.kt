package com.example.syncup.data.repository.event

import com.example.syncup.data.dto.EventForVotingDto
import com.example.syncup.data.dto.EventSummaryDto
import com.example.syncup.data.dto.EventTypeDto
import com.example.syncup.data.dto.TimeSlotDto
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventType
import com.example.syncup.data.model.events.TimeSlot

fun EventSummaryDto.toEvent() : Event{
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

fun EventForVotingDto.toEvent() : Event{
    val votes = this.slots
    val possibleSlots = votes.map { it.timeSlot.toTimeSlot() }.toSet()
    val myVotes = votes.associate { it.timeSlot.toTimeSlot() to it.myVote }
    val slotCounts = votes.associate { it.timeSlot.toTimeSlot() to it.votes }
    val event = Event(
        id = id,
        groupId = groupId,
        title = name,
        possibleSlots = possibleSlots,
        description = description,
        eventTypeId = eventTypeId
    )
    event.myVotes = myVotes
    event.slotCounts = slotCounts
    return event
}

fun TimeSlotDto.toTimeSlot() : TimeSlot{
    return TimeSlot(
        date = date,
        partOfDay = partOfDay
    )
}

fun TimeSlot.toTimeSlotDto() : TimeSlotDto{
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