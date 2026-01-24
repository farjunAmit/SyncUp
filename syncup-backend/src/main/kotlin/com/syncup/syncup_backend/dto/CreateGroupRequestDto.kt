package com.syncup.syncup_backend.dto

data class CreateGroupRequestDto(
    val id: Long,
    val name: String,
    val invitedEmails : List<String>
)
