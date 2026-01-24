package com.syncup.syncup_backend.exceptions

class UserNotFoundByEmailException(email: String) :
    RuntimeException("User not found with email: $email")
