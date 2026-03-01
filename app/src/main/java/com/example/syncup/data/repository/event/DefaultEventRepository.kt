package com.example.syncup.data.repository.event

import com.example.syncup.data.dto.EventCreateRequestDto
import com.example.syncup.data.dto.EventTypeCreateRequestDto
import com.example.syncup.data.dto.SubmitVoteRequestDto
import com.example.syncup.data.dto.VoteDto
import com.example.syncup.data.local.EventDao
import com.example.syncup.data.mapper.toEntity
import com.example.syncup.data.mapper.toEvent
import com.example.syncup.data.mapper.toEventType
import com.example.syncup.data.mapper.toTimeSlotDto
import com.example.syncup.data.model.events.DecisionMode
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventType
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote
import com.example.syncup.data.remote.event.EventRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultEventRepository @Inject constructor(
    private val eventRemoteDataSource: EventRemoteDataSource,
    private val eventDao: EventDao
) : EventRepository {

    override fun observeAll(groupId: Long): Flow<List<Event>> {
        return eventDao.getEventsByGroup(groupId).map { entities ->
            entities.map { it.toEvent() }
        }
    }

    override fun observeById(id: Long): Flow<Event?> {
        return eventDao.getEventById(id).map { it?.toEvent() }
    }

    override fun observeEventTypes(groupId: Long): Flow<Map<Long, EventType>> {
        return eventDao.getEventTypesByGroup(groupId).map { entities ->
            entities.associate { it.id to it.toEventType() }
        }
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
        val eventSummaryDto = eventRemoteDataSource.createEvent(groupId, eventCreateDto)
        val eventDetailDto = eventRemoteDataSource.getEvent(eventSummaryDto.id)
        val event = eventDetailDto.toEvent()
        
        eventDao.upsertEvent(event.toEntity())
        return event
    }

    override suspend fun delete(eventId: Long) {
        eventRemoteDataSource.deleteEvent(eventId)
        eventDao.deleteEventById(eventId)
    }

    override suspend fun submitVote(
        eventId: Long,
        voteDraft: Map<TimeSlot, Vote?>
    ): Event {
        val submitVoteDto = SubmitVoteRequestDto(
            eventId = eventId,
            votes = voteDraft.map { (slot, vote) -> VoteDto(slot.toTimeSlotDto(), vote) }
        )
        val eventSummaryDto = eventRemoteDataSource.submitVotes(submitVoteDto)
        val eventDetailDto = eventRemoteDataSource.getEvent(eventSummaryDto.id)
        val event = eventDetailDto.toEvent()
        
        eventDao.upsertEvent(event.toEntity())
        return event
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
        val eventTypeDto = eventRemoteDataSource.createEventType(eventCreateDto)
        val eventType = eventTypeDto.toEventType()
        eventDao.upsertEventType(eventType.toEntity())
        return eventType
    }

    override suspend fun refresh(groupId: Long) {
        // Refresh Events
        val remoteEvents = eventRemoteDataSource.getEvents(groupId)
        val detailedEvents = remoteEvents.map { summary ->
            eventRemoteDataSource.getEvent(summary.id).toEvent()
        }
        
        if (detailedEvents.isEmpty()) {
            eventDao.clearEventsByGroup(groupId)
        } else {
            eventDao.deleteEventsNotIn(groupId, detailedEvents.map { it.id })
            eventDao.upsertEvents(detailedEvents.map { it.toEntity() })
        }

        // Refresh Event Types
        val remoteTypes = eventRemoteDataSource.getEventTypes(groupId)
        val eventTypeEntities = remoteTypes.map { it.toEventType().toEntity() }
        
        eventDao.clearEventTypesByGroup(groupId)
        eventDao.upsertEventTypes(eventTypeEntities)
    }
}
