package com.syncup.syncup_backend.dto

import com.syncup.syncup_backend.model.TimeSlot
import com.syncup.syncup_backend.model.Vote

data class SlotVotingSummaryDto(
    val timeSlot: TimeSlotDto,
    val votes: Map<Vote, Int>,
    val myVote: Vote?
)
