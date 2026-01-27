package com.syncup.syncup_backend.dto

data class SubmitVoteRequestDto(
    val eventId: Long,
    val votes: List<VoteDto>
)