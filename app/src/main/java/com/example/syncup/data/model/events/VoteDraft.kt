package com.example.syncup.data.model.events

data class VoteDraft(
    val userId: String,
    val votes: Map<TimeSlot, Vote?>
)
