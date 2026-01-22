package com.example.syncup.data.repository.event

import com.example.syncup.data.model.events.DecisionMode
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventStatus
import com.example.syncup.data.model.events.EventType
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote
import com.example.syncup.data.model.events.VoteDraft
import com.example.syncup.data.repository.group.GroupsRepository
import java.util.UUID

/**
 * In-memory implementation of [EventRepository].
 *
 * Purpose:
 * - Used in the MVP stage as a simple data source (no database / no backend).
 * - Stores events in a local list and provides basic CRUD operations.
 *
 * Key behavior:
 * - After a vote is submitted, the repository checks whether all group members have voted.
 * - If all members voted, it automatically selects a final time slot according to [DecisionMode].
 * - When a final slot is found, the event transitions to [EventStatus.DECIDED].
 *
 * Dependencies:
 * - [GroupsRepository] is injected so that the repository can access group membership count
 *   (needed to determine when "everyone voted").
 */
class InMemoryEventRepository() : EventRepository {

    /**
     * Local in-memory storage for events.
     *
     * Note:
     * - This list lives only in RAM. When the app restarts, data is lost.
     * - In later phases, this will be replaced by a database or remote backend.
     */
    private val events = mutableListOf<Event>()
    private val eventTypes = mutableListOf<EventType>()

    /**
     * Returns all events that belong to the given group.
     *
     * In-memory implementation:
     * - Filters the local list by groupId.
     */
    override suspend fun getAll(groupId: String): List<Event> {
        return events.filter { it.groupId == groupId }
    }

    /**
     * Retrieves a single event by its ID.
     *
     * In-memory implementation:
     * - Finds the first matching event in the local list.
     */
    override suspend fun getById(id: String): Event? {
        return events.find { it.id == id }
    }

    /**
     * Creates a new event and stores it in memory.
     *
     * Implementation details:
     * - Uses a random UUID as the event ID.
     * - Event is created in its initial state (typically VOTING, depends on Event constructor).
     */
    override suspend fun create(
        groupId: String,
        title: String,
        possibleSlots: Set<TimeSlot>,
        description: String,
        decisionMode: DecisionMode,
        eventTypeId: String?
    ): Event {
        val eventId = UUID.randomUUID().toString()
        val newEvent = Event(eventId, groupId, title, possibleSlots, description, decisionMode, eventTypeId)
        events.add(newEvent)
        return newEvent
    }

    /**
     * Deletes an event by ID.
     *
     * In-memory implementation:
     * - Removes matching event(s) from the local list.
     */
    override suspend fun delete(eventId: String) {
        events.removeIf { it.id == eventId }
    }

    /**
     * Submits or updates a user's vote for a specific event.
     *
     * Flow:
     * 1) Find the event by ID.
     * 2) Persist/update the user's vote in the event (via event.setNewVotesForUser).
     * 3) Determine group member count via [groupRepo].
     * 4) If number of voters == number of members:
     *    - Select a final date automatically according to [DecisionMode]
     *    - If a final date is found, set it and mark the event as DECIDED.
     *
     * Notes:
     * - If no final date is found, the TODO indicates future handling (e.g. NO_MATCH + notification).
     * - This method is the "single point" where vote submission can trigger state transitions.
     */
    override suspend fun submitVote(eventId: String, voteDraft: VoteDraft, memberCount: Int) {
        val event = events.find { it.id == eventId } ?: return

        // Store/update the user's votes for this event
        event.setNewVotesForUser(voteDraft)

        // Auto-finalize only when everyone has voted
        if (memberCount == event.userVotes.size) {

            // Pick a final slot according to the event's decision mode
            val finalDate = when (event.decisionMode) {
                DecisionMode.ALL_OR_NOTHING -> allOrNothingModeCalculate(event, memberCount)
                DecisionMode.POINTS -> pointsModeCalculate(event)
            }

            if (finalDate == null) {
                // TODO: Handle "no suitable date" outcome
                // Example future behavior:
                // - set a dedicated status (e.g. NO_MATCH)
                // - notify the creator / members to propose new slots
            } else {
                // Persist the chosen final slot and mark the event as decided
                event.setFinalDate(finalDate)
                event.updateStatus(EventStatus.DECIDED)
            }
        }
    }

    override suspend fun getEventTypesForGroup(groupId: String): Map<String, EventType> {
        val eventTypesForGroup = eventTypes.filter { it.groupId == groupId }
        val result = mutableMapOf<String, EventType>()
        eventTypesForGroup.forEach { eventType ->
            result[eventType.id] = eventType
        }
        return result
    }

    override suspend fun getEventTypesAsList(groupId: String): List<EventType> {
        return eventTypes.filter { it.groupId == groupId }
    }

    override suspend fun addEventType(
        groupId: String,
        type: String,
        color: Long
    ) : EventType {
        val eventType = EventType(UUID.randomUUID().toString(), groupId, type, color)
        eventTypes.add(eventType)
        return eventType
    }

    /**
     * Calculates a final slot for [DecisionMode.ALL_OR_NOTHING].
     *
     * Current rule (as implemented):
     * - A slot is immediately disqualified if ANY user vote is:
     *   - null (user did not provide a vote for this slot)
     *   - Vote.NO
     * - Among the remaining "valid" slots:
     *   - If everyone voted YES => return that slot immediately.
     *   - Otherwise, choose the slot with the highest YES count (bestCanVote).
     *
     * @return The best matching [TimeSlot], or null if no slot is valid.
     */
    private fun allOrNothingModeCalculate(event: Event, memberCount: Int): TimeSlot? {
        var voteCount = 0
        var canVoteCount = 0
        var bestCanVote = 0
        var bestTimeSlot: TimeSlot? = null

        slotLoop@ for (slot in event.possibleSlots) {

            // Check each participant's vote for this slot
            for (it in event.userVotes) {
                val userVote = it.value.getVote(slot)

                // If any member cannot / did not vote for this slot -> slot is disqualified
                if (userVote == null || userVote == Vote.NO) {
                    voteCount = 0
                    canVoteCount = 0
                    continue@slotLoop
                } else {
                    voteCount++
                    if (userVote == Vote.YES) {
                        canVoteCount++
                    }
                }
            }

            // Slot is considered only if we collected a vote from all members
            if (voteCount == memberCount) {

                // Perfect match: everyone said YES
                if (canVoteCount == memberCount) {
                    return slot
                }

                // Otherwise, keep the slot with the highest number of YES votes
                if (canVoteCount > bestCanVote) {
                    bestCanVote = canVoteCount
                    bestTimeSlot = slot
                }
            }

            // Reset counters for the next slot
            voteCount = 0
            canVoteCount = 0
        }

        return bestTimeSlot
    }

    /**
     * Calculates a final slot for [DecisionMode.POINTS].
     *
     * Scoring:
     * - Vote.YES     -> 2 points
     * - Vote.YES_BUT -> 1 point
     * - Vote.NO / null -> 0 points
     *
     * Selection:
     * - Choose the slot with the highest total score.
     * - If all slots score 0, returns null (no suitable slot).
     *
     * @return The best scoring [TimeSlot], or null if no slot has any points.
     */
    private fun pointsModeCalculate(event: Event): TimeSlot? {
        var bestPoints = 0
        var bestTimeSlot: TimeSlot? = null

        for (slot in event.possibleSlots) {
            var points = 0

            for (it in event.userVotes) {
                val userVote = it.value.getVote(slot)
                when (userVote) {
                    Vote.YES -> points += 2
                    Vote.YES_BUT -> points++
                    else -> {}
                }
            }

            if (points > bestPoints) {
                bestPoints = points
                bestTimeSlot = slot
            }
        }

        return bestTimeSlot
    }
}
