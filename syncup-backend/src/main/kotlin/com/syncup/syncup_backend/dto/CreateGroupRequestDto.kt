package com.syncup.syncup_backend.dto

data class CreateGroupRequestDto(
    val name: String,
    val invitedEmails : List<String>
)
