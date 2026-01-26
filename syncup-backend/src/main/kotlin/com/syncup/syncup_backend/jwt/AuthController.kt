package com.syncup.syncup_backend.jwt

import com.syncup.syncup_backend.dto.AuthResponseDto
import com.syncup.syncup_backend.dto.LoginRequestDto
import com.syncup.syncup_backend.dto.RegisterRequestDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequestDto): AuthResponseDto =
        authService.register(registerRequest)

    @PostMapping("/login")
    fun login(@RequestBody loginRequestDto: LoginRequestDto): AuthResponseDto =
        authService.login(loginRequestDto)
}
