package com.example.syncup.data.model.events

import java.util.Date

class Event(
    val id: String,
    val groupId: String,
    title: String,
    possibleSlots: List<TimeSlot>,
    description: String
) {
    var title: String = title
        private set
    var possibleSlots: List<TimeSlot> = possibleSlots
        private set
    var description: String = description
        private set
    var eventStatus = EventStatus.VOTING
        private set
    var finalDate: TimeSlot? = null
        private set

    private val _userVotes: MutableMap<String, UserVotes> =
        mutableMapOf()
    val userVotes: Map<String, UserVotes>
        get() = _userVotes

    /**
     * Add a vote for a specific user and date.
     */
    fun setVote(userId: String, date: TimeSlot, vote: Vote) {
        //Todo: Add validation to ensure date is in possibleDates and the EventStatus is VOTING
        val userVotes = _userVotes.getOrPut(userId) { UserVotes(userId) }
        userVotes.addVote(date, vote)
    }

    /**
     * Set new set of votes for a specific user.
     */
    fun setNewVotesForUser(userId: String, votes: Map<TimeSlot, Vote>){
        //Todo: Add validation to ensure date is in possibleDates and the EventStatus is VOTING
        val userVotes = _userVotes.getOrPut(userId) { UserVotes(userId) }
        userVotes.clear()
        votes.forEach { (date, vote) ->
            userVotes.addVote(date, vote)
        }
    }
}

enum class EventStatus {
    VOTING,
    DECIDED,
    CANCELLED
}