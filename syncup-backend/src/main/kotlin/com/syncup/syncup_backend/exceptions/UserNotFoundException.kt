package com.syncup.syncup_backend.exceptions

class UserNotFoundException(
    val userId: Long
) : RuntimeException(
    "User with id $userId as id not found"
)