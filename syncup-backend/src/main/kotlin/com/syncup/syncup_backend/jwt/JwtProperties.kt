package com.syncup.syncup_backend.jwt
import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    lateinit var secret: String
    var expirationMinutes: Long = 0
}
