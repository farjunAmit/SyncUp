package com.example.syncup.data.dto

data class EventForVotingDto(
    val id: Long,
    val groupId: Long,
    val name: String,
    val description: String,
    val eventTypeId: Long?,
    val slots: List<SlotVotingSummaryDto>
)
