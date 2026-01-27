package com.syncup.syncup_backend.dto

data class EventTypeCreateRequestDto(
    val groupId: Long,
    val type: String,
    val color: Long
)
