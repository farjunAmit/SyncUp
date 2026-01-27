package com.example.syncup.data.repository.event

import android.util.Log
import com.example.syncup.data.dto.EventCreateRequestDto
import com.example.syncup.data.dto.EventTypeCreateRequestDto
import com.example.syncup.data.dto.SubmitVoteRequestDto
import com.example.syncup.data.dto.VoteDto
import com.example.syncup.data.model.events.DecisionMode
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventType
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote
import retrofit2.HttpException
import javax.inject.Inject

class DefaultEventRepository @Inject constructor(
    private val eventRemoteDataSource: EventRemoteDataSource
) : EventRepository {

    override suspend fun getAll(groupId: Long): List<Event> {
        val events = eventRemoteDataSource.getEvents(groupId)
        return events.map { it.toEvent() }
    }

    override suspend fun getById(id: Long): Event? {
        try {
            val event = eventRemoteDataSource.getEvent(id)
            return event.toEvent()
        } catch (e: HttpException) {
            Log.e("EventRepository", "Error fetching event: ${e.response()?.errorBody()?.string()}")
        }
        return TODO("Provide the return value")
    }

    override suspend fun create(
        groupId: Long,
        title: String,
        possibleSlots: Set<TimeSlot>,
        description: String,
        decisionMode: DecisionMode,
        eventTypeId: Long?
    ): Event {
        val eventCreateDto = EventCreateRequestDto(
            name = title,
            description = description,
            decisionMode = decisionMode,
            eventTypeId = eventTypeId,
            possibleSlots = possibleSlots.map { it.toTimeSlotDto() }
        )
        val event = eventRemoteDataSource.createEvent(groupId, eventCreateDto)
        return event.toEvent()
    }

    override suspend fun delete(eventId: Long) {
        eventRemoteDataSource.deleteEvent(eventId)
    }

    override suspend fun submitVote(
        eventId: Long,
        voteDraft: Map<TimeSlot, Vote?>
    ): Event {
        val submitVoteDto = SubmitVoteRequestDto(
            eventId = eventId,
            votes = voteDraft.map { (slot, vote) -> VoteDto(slot.toTimeSlotDto(), vote) }
        )
        val event = eventRemoteDataSource.submitVotes(submitVoteDto)
        return event.toEvent()
    }

    override suspend fun getEventTypesForGroup(groupId: Long): Map<Long, EventType> {
        val eventTypes = eventRemoteDataSource.getEventTypes(groupId)
        return eventTypes.associateBy({ it.id }, { it.toEventType() })
    }

    override suspend fun addEventType(
        groupId: Long,
        type: String,
        color: Long
    ): EventType {
        val eventCreateDto = EventTypeCreateRequestDto(
            groupId = groupId,
            type = type,
            color = color
        )
        val eventType = eventRemoteDataSource.createEventType(eventCreateDto)
        return eventType.toEventType()
    }
}