package com.example.syncup.data.dto

data class SubmitVoteRequestDto(
    val eventId: Long,
    val userId: Long,
    val votes: List<VoteDto>
)
