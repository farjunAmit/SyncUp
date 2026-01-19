package com.example.syncup.data.repository.event

import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.TimeSlot

/**
 * Repository interface for managing [Event] data.
 *
 * This interface defines the contract for accessing and modifying
 * event-related data, regardless of the underlying data source
 * (e.g. in-memory, local database, or remote backend).
 *
 * Implementations of this repository are responsible for data retrieval,
 * persistence, and mapping, while higher layers should rely only on this
 * abstraction.
 */
interface EventRepository {
    /**
     * Returns all events that belong to the given group.
     *
     * @param groupId The ID of the group whose events are requested.
     */
    suspend fun getAll(groupId: String): List<Event>

    /**
     * Retrieves a single event by its unique identifier.
     *
     * @param id The ID of the event.
     * @return The matching [Event], or null if no such event exists.
     */
    suspend fun getById(id: String): Event?

    /**
     * Creates a new event for the specified group.
     *
     * @param groupId The ID of the group that owns the event.
     * @param title The event title.
     * @param possibleSlots All time slots that participants can vote for.
     * @param description Optional descriptive text for the event.
     *
     * @return The newly created [Event].
     */
    suspend fun create(
        groupId: String,
        title: String,
        possibleSlots: Set<TimeSlot>,
        description: String
    ): Event

    /**
     * Deletes an event by its identifier.
     *
     * @param eventId The ID of the event to delete.
     */
    suspend fun delete(eventId: String)

}