package com.syncup.syncup_backend.exceptions

class UserNotFoundException(val userId: Long) :
    RuntimeException("User with id $userId as id not found")

class UserNotFoundByEmailException(email: String) :
    RuntimeException("User not found with email: $email")

class InvalidPasswordOrEmailException() :
    RuntimeException("Invalid password or email")

class UserAlreadyExistsException(email: String) :
    RuntimeException("User already exists with email: $email")

class HashPasswordFailedException() :
    RuntimeException("Failed to hash password")