package com.example.syncup.data.model.events

import java.util.Date

class UserVotes(val userId: Int) {

    private val votes = mutableMapOf<Date, Vote>()

    fun addVote(date: Date, vote: Vote) {
        votes[date] = vote
    }

    fun getVote(date: Date): Vote? {
        return votes[date]
    }

    fun getAllVotes(): Map<Date, Vote> {
        return votes.toMap()
    }
}
