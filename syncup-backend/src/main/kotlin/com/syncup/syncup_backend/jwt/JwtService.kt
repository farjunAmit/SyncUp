package com.syncup.syncup_backend.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JwtService(
    private val props: JwtProperties
) {
    fun generateToken(userId: Long): String {
        val now = Date()
        val exp = Date(now.time + props.expirationMinutes * 60_000)

        val key = Keys.hmacShaKeyFor(props.secret.toByteArray())

        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(now)
            .setExpiration(exp)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun extractUserId(token: String): Long {
        val key = Keys.hmacShaKeyFor(props.secret.toByteArray())

        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        return claims.subject.toLong()
    }
}
