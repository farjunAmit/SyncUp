package com.example.syncup.data.repository.event

import com.example.syncup.data.model.events.DecisionMode
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventType
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.VoteDraft

class DefaultEventRepository : EventRepository {
    override suspend fun getAll(groupId: Long): List<Event> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: Long): Event? {
        TODO("Not yet implemented")
    }

    override suspend fun create(
        groupId: Long,
        title: String,
        possibleSlots: Set<TimeSlot>,
        description: String,
        decisionMode: DecisionMode,
        eventTypeId: Long?
    ): Event {
        TODO("Not yet implemented")
    }

    override suspend fun delete(eventId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun submitVote(
        eventId: Long,
        voteDraft: VoteDraft,
        memberCount: Int
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getEventTypesForGroup(groupId: Long): Map<Long, EventType> {
        TODO("Not yet implemented")
    }

    override suspend fun getEventTypesAsList(groupId: Long): List<EventType> {
        TODO("Not yet implemented")
    }

    override suspend fun addEventType(
        groupId: Long,
        type: String,
        color: Long
    ): EventType {
        TODO("Not yet implemented")
    }
}