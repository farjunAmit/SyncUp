package com.example.syncup.data.dto

import com.example.syncup.data.model.events.Vote

data class VoteDto(
    val timeSlotDto: TimeSlotDto,
    val vote: Vote?
)
