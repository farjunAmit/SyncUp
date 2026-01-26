package com.syncup.syncup_backend.jwt
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secret: String = "",
    val expirationMinutes: Long = 0
)
