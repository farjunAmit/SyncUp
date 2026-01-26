package com.example.syncup.data.repository.auth

import com.example.syncup.data.model.LoginStatus

interface AuthRepository {
    suspend fun login(email: String, password: String): LoginStatus
    suspend fun register(username: String, email: String, password: String): LoginStatus
}