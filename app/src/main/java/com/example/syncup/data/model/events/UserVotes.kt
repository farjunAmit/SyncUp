package com.example.syncup.data.model.events

import java.util.Date

class UserVotes(val userId: String) {

    private val votes = mutableMapOf<TimeSlot, Vote>()

    fun addVote(date: TimeSlot, vote: Vote) {
        votes[date] = vote
    }

    fun getVote(date: TimeSlot): Vote? {
        return votes[date]
    }

    fun getAllVotes(): Map<TimeSlot, Vote> {
        return votes.toMap()
    }

    fun clear(){
        votes.clear()
    }
}
