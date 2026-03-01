package com.example.syncup.data.repository.event

import com.example.syncup.data.model.events.DecisionMode
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventType
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing [Event] data.
 *
 * This interface defines the contract for accessing and modifying
 * event-related data, regardless of the underlying data source
 * (e.g. in-memory, local database, or remote backend).
 */
interface EventRepository {
    
    /**
     * Observes all events that belong to the given group from local storage.
     */
    fun observeAll(groupId: Long): Flow<List<Event>>

    /**
     * Observes a single event by its unique identifier from local storage.
     */
    fun observeById(id: Long): Flow<Event?>

    /**
     * Observes event types for the given group from local storage.
     */
    fun observeEventTypes(groupId: Long): Flow<Map<Long, EventType>>

    /**
     * Creates a new event for the specified group and updates local storage.
     */
    suspend fun create(
        groupId: Long,
        title: String,
        possibleSlots: Set<TimeSlot>,
        description: String,
        decisionMode: DecisionMode,
        eventTypeId: Long?
    ): Event

    /**
     * Deletes an event by its identifier and updates local storage.
     */
    suspend fun delete(eventId: Long)

    /**
     * Submits or updates a user's vote for the given event and updates local storage.
     */
    suspend fun submitVote(
        eventId: Long,
        voteDraft: Map<TimeSlot, Vote?>
    ) : Event

    /**
     * Adds a new event type and updates local storage.
     */
    suspend fun addEventType(groupId: Long, type: String, color: Long) : EventType
    
    /**
     * Refreshes the local cache for a specific group from the remote source.
     */
    suspend fun refresh(groupId: Long)
}
