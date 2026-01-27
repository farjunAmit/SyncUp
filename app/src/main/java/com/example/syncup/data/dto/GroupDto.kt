package com.example.syncup.data.dto

data class GroupDto(
    val id: Long,
    val name: String,
    val members: List<UserDto>
)
