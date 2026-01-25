package com.syncup.syncup_backend.dto

import com.syncup.syncup_backend.model.Vote

data class VoteDto(
    val timeSlotDto: TimeSlotDto,
    val vote: Vote?
)
