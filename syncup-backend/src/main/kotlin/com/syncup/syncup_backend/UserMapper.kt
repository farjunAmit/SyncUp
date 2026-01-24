package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.UserDto
import com.syncup.syncup_backend.entity.UserEntity

fun UserDto.toUserEntity(password: String): UserEntity {
    return UserEntity(
        username = this.username,
        email = this.email,
        password = password
    )
}

fun UserEntity.toUserDto(): UserDto{
    return UserDto(
        id = this.id,
        username = this.username,
        email = this.email
    )
}