package com.example.syncup.data.dto

import com.example.syncup.data.model.events.Vote

data class SlotVotingSummaryDto(
    val timeSlot: TimeSlotDto,
    val votes: Map<Vote, Int>,
    val myVote: Vote?
)
