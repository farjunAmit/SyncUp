package com.example.syncup.data.dto

data class CreateGroupRequestDto(
    val name: String,
    val invitedEmails : List<String>
)
