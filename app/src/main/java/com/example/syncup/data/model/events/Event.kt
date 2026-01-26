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
    val eventTypeId: Long? = null
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


    /**
     * Internal mutable storage of votes per user.
     * Each user can have at most one [UserVotes] entry.
     */
    private val _userVotes: MutableMap<Long, UserVotes> =
        mutableMapOf()

    /**
     * Read-only view of all user votes for this event.
     */
    val userVotes: Map<Long, UserVotes>
        get() = _userVotes

    /**
     * Adds or updates a single vote for a specific user and time slot.
     *
     * If the user has not voted before, a new [UserVotes] entry is created.
     *
     * @param userId The ID of the user casting the vote.
     * @param date The [TimeSlot] being voted on.
     * @param vote The vote value (e.g. available, maybe, unavailable).
     *
     * Note:
     * Validation such as ensuring the date exists in [possibleSlots] or
     * that the event is still in [EventStatus.VOTING] is currently not enforced.
     */
    fun setVote(userId: Long, date: TimeSlot, vote: Vote) {
        //Todo: Add validation to ensure date is in possibleDates and the EventStatus is VOTING
        val userVotes = _userVotes.getOrPut(userId) { UserVotes(userId) }
        userVotes.addVote(date, vote)
    }

    /**
     * Replaces all existing votes of a user with a new set of votes.
     *
     * This is useful when submitting a full voting form rather than
     * individual vote updates.
     *
     * @param userId The ID of the user whose votes are being replaced.
     * @param votes A map of [TimeSlot] to [Vote].
     *
     * Note:
     * Existing votes for the user are cleared before applying the new ones.
     */
    fun setNewVotesForUser(voteDraft: VoteDraft) {
        //Todo: Add validation to ensure date is in possibleDates and the EventStatus is VOTING
        val userVotes = _userVotes.getOrPut(voteDraft.userId) { UserVotes(voteDraft.userId) }
        userVotes.clear()
        voteDraft.votes.forEach { (date, vote) ->
            userVotes.addVote(date, vote)
        }
    }

    /**
     * Returns the current vote map for a specific user within this event.
     *
     * @param userId The identifier of the user (currently can be email/userId).
     * @return A map of [TimeSlot] -> [Vote?] representing the user's selections per slot.
     *
     * Notes:
     * - If the user has never submitted a vote for this event, an empty map is returned.
     * - The returned map should be treated as read-only from the caller side.
     */
    fun getVoteForUser(userId: Long): Map<TimeSlot, Vote?> {
        val userVotes = _userVotes[userId] ?: return emptyMap()
        return userVotes.getAllVotes()
    }

    /**
     * Updates the lifecycle status of the event.
     *
     * Typical transitions:
     * - [EventStatus.VOTING] -> [EventStatus.DECIDED] when a final slot is chosen.
     * - [EventStatus.VOTING] -> [EventStatus.CANCELLED] if the event is cancelled.
     *
     */
    fun updateStatus(newStatus: EventStatus) {
        //Todo: Add validation to ensure status is valid
        eventStatus = newStatus
    }

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
    CANCELLED
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