package com.example.syncup.data.model.events

data class VoteDraft(
    val userId: Long, val votes: Map<TimeSlot, Vote?> = emptyMap()
)
