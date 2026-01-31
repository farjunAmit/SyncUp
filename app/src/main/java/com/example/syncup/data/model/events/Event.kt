package com.example.syncup.data.model.events

/**
 * Represents a group event that is created and managed through the voting flow.
 *
 * An Event belongs to a specific group and is defined by a set of possible
 * {@link TimeSlot}s that participants can vote on.
 *
 * Lifecycle:
 * - Starts in [EventStatus.VOTING]
 * - Moves to [EventStatus.DECIDED] once a final date is chosen
 * - Can be [EventStatus.CANCELLED] if the event is aborted
 *
 * The Event itself is responsible for managing user votes, while validation
 * rules (such as permissions or backend constraints) may be enforced elsewhere.
 */
class Event(
    val id: Long,
    val groupId: Long,
    title: String,
    possibleSlots: Set<TimeSlot>,
    description: String,
    val decisionMode: DecisionMode = DecisionMode.ALL_OR_NOTHING,
    val eventTypeId: Long? = null,
) {
    var title: String = title
        private set

    /**
     * All possible time slots that participants can vote for.
     * This set is defined by the event creator during event creation
     * and does not change after voting starts.
     */
    var possibleSlots: Set<TimeSlot> = possibleSlots
        private set
    var description: String = description
        private set
    var eventStatus = EventStatus.VOTING
        private set

    /**
     * The final chosen time slot for the event.
     * This value is null while the event is in [EventStatus.VOTING].
     */
    var finalDate: TimeSlot? = null
        private set

    var myVotes: Map<TimeSlot, Vote?> = emptyMap()

    var slotCounts: Map<TimeSlot, Map<Vote, Int>> = emptyMap()

    /**
     * Sets the final chosen time slot for this event.
     *
     * @param date The chosen [TimeSlot], or null if no suitable slot could be selected.
     *
     * Notes:
     * - We use copy() to avoid accidental external mutation if [TimeSlot] is a mutable type
     *   or to ensure immutability semantics.
     * - In the typical flow:
     *   - This is set only when the event transitions to [EventStatus.DECIDED].
     *   - When no final date is found, the event should remain in VOTING or move to a
     *     dedicated "no match" handling flow (future).
     */
    fun setFinalDate(date: TimeSlot?) {
        finalDate = date?.copy()
    }

    fun setEventStatus(status: EventStatus) {
        eventStatus = status
    }
}

/**
 * Represents the current lifecycle state of an [Event].
 */
enum class EventStatus {
    /** Event is open for voting by participants */
    VOTING,

    /** A final time slot has been chosen */
    DECIDED,

    /** Event was cancelled and is no longer active */
    CANCELLED,

    /** Event has not been resolved yet */
    UNRESOLVED
}

/**
 * Determines how the system selects the final time slot once all members have voted.
 *
 * - ALL_OR_NOTHING:
 *   Choose a slot only if it satisfies the strict group constraint (defined in logic),
 *   otherwise no final date is chosen.
 *
 * - POINTS:
 *   Each vote value contributes points to a slot, and the slot with the highest score wins.
 */
enum class DecisionMode {
    ALL_OR_NOTHING,
    POINTS
}