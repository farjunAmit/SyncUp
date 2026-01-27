package com.syncup.syncup_backend.dto

data class GroupDto(
    val id: Long,
    val name: String,
    val members: List<UserDto>
)
