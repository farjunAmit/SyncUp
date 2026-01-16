package com.example.syncup.data.repository.event

import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.TimeSlot
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun getAll(groupId: String): List<Event>
    suspend fun getById(id: String): Event?
    suspend fun create(groupId: String, title: String, possibleSlots: List<TimeSlot>, description: String): Event
    suspend fun delete(eventId: String)
}