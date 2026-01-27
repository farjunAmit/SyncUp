package com.syncup.syncup_backend.services

import com.syncup.syncup_backend.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository
){
}