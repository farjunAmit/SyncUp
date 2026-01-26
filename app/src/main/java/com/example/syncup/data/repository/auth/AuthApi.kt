package com.example.syncup.data.repository.auth

import com.example.syncup.data.dto.AuthResponseDto
import com.example.syncup.data.dto.LoginRequestDto
import com.example.syncup.data.dto.RegisterRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(@Body body: LoginRequestDto): Response<AuthResponseDto>

    @POST("register")
    suspend fun register(@Body body: RegisterRequestDto): Response<AuthResponseDto>
}