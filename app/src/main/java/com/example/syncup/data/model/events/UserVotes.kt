package com.example.syncup.data.model.events

/**
 * Holds all voting decisions of a single user for a specific event.
 *
 * Each user can vote on multiple [TimeSlot]s, where each slot
 * is associated with a single [Vote] value.
 *
 * This class is owned and managed by an [Event] instance and
 * should not be modified directly from outside the event domain.
 */
class UserVotes(val userId: String) {

    /**
     * Internal mutable storage of the user's votes.
     * Keyed by [TimeSlot] to ensure a single vote per slot.
     */
    private val votes = mutableMapOf<TimeSlot, Vote?>()

    /**
     * Adds or updates a vote for the given [TimeSlot].
     *
     * If a vote for the slot already exists, it will be replaced.
     */
    fun addVote(date: TimeSlot, vote: Vote?) {
        votes[date] = vote
    }

    /**
     * Returns the vote for a specific [TimeSlot], or null if the user
     * has not voted for that slot.
     */
    fun getVote(date: TimeSlot): Vote? {
        return votes[date]
    }

    /**
     * Returns an immutable copy of all votes cast by the user.
     */
    fun getAllVotes(): Map<TimeSlot, Vote?> {
        return votes.toMap()
    }

    /**
     * Removes all votes of the user.
     * Typically used when resubmitting a full voting form.
     */
    fun clear(){
        votes.clear()
    }
}
