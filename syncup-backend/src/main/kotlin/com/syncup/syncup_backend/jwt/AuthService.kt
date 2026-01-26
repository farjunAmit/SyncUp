package com.syncup.syncup_backend.jwt

import com.syncup.syncup_backend.config.SecurityBeansConfig
import com.syncup.syncup_backend.dto.AuthResponseDto
import com.syncup.syncup_backend.dto.LoginRequestDto
import com.syncup.syncup_backend.dto.RegisterRequestDto
import com.syncup.syncup_backend.exceptions.HashPasswordFailedException
import com.syncup.syncup_backend.exceptions.InvalidPasswordOrEmailException
import com.syncup.syncup_backend.exceptions.UserAlreadyExistsException
import com.syncup.syncup_backend.repositories.UserRepository
import com.syncup.syncup_backend.toUserEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService (
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
){
    fun register(registerRequest: RegisterRequestDto): AuthResponseDto {
        if (userRepository.existsByEmail(registerRequest.email)) {
            throw UserAlreadyExistsException(registerRequest.email)
        }
        val hashedPassword =
            passwordEncoder.encode(registerRequest.password) ?: throw HashPasswordFailedException()
        val user = userRepository.save(registerRequest.toUserEntity(hashedPassword))
        val token = jwtService.generateToken(user.id)
        return AuthResponseDto(
            userId = user.id,
            token = token
        )
    }

    fun login(loginRequestDto: LoginRequestDto): AuthResponseDto {
        val user = userRepository.findByEmail(loginRequestDto.email)

        if (user == null || !passwordEncoder.matches(loginRequestDto.password, user.password)) {
            throw InvalidPasswordOrEmailException()
        }

        val token = jwtService.generateToken(user.id)
        return AuthResponseDto(
            userId = user.id,
            token = token
        )
    }
}