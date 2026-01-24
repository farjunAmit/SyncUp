package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.GroupDto
import com.syncup.syncup_backend.dto.UserDto
import com.syncup.syncup_backend.entity.GroupEntity
import com.syncup.syncup_backend.entity.UserEntity

fun GroupEntity.toDto(): GroupDto {
    return GroupDto(
        id = this.id,
        name = this.name,
    )
}

fun GroupDto.toEntity(): GroupEntity{
    return GroupEntity(
        name = this.name,
    )
}

fun UserDto.toEntity(password: String): UserEntity {
    return UserEntity(
        username = this.username,
        email = this.email,
        password = password
    )
}

fun UserEntity.toDto(): UserDto{
    return UserDto(
        id = this.id,
        username = this.username,
        email = this.email
    )
}