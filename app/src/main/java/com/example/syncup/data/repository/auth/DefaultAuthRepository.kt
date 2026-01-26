package com.example.syncup.data.repository.auth

import com.example.syncup.data.dto.LoginRequestDto
import com.example.syncup.data.model.LoginStatus
import com.example.syncup.data.session.SessionStore
import javax.inject.Inject

class DefaultAuthRepository@Inject constructor(
    private val authApi: AuthApi,
    private val sessionStore : SessionStore
) : AuthRepository {
    override suspend fun login(email: String, password: String): LoginStatus {
        val loginRequest = LoginRequestDto(email, password)
        val response = authApi.login(loginRequest)
        if (response.isSuccessful) {
            val loginResponse = response.body()
            val token = loginResponse?.token
            if (token != null) {
                sessionStore.saveToken(token)
            }
            return LoginStatus(true)
        }
        else{
            return LoginStatus(false, response.message())
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): LoginStatus {
        TODO("Not yet implemented")
    }

}