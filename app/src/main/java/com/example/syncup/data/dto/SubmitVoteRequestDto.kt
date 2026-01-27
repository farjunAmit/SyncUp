package com.example.syncup.data.dto

data class SubmitVoteRequestDto(
    val eventId: Long,
    val votes: List<VoteDto>
)
