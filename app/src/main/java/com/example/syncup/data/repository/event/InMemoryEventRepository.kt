package com.example.syncup.data.repository.event

import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.TimeSlot
import java.util.UUID

class InMemoryEventRepository : EventRepository {
    private val events = mutableListOf<Event>()

    override suspend fun getAll(groupId: String): List<Event> {
        return events.filter { it.groupId == groupId }
    }

    override suspend fun getById(id: String): Event? {
        return events.find { it.id == id }
    }

    override suspend fun create(
        groupId: String,
        title: String,
        possibleSlots: Set<TimeSlot>,
        description: String
    ): Event {
        val eventId = UUID.randomUUID().toString()
        val newEvent = Event(eventId, groupId, title, possibleSlots, description)
        events.add(newEvent)
        return newEvent
    }

    override suspend fun delete(eventId: String) {
        events.removeIf { it.id == eventId }
    }

}