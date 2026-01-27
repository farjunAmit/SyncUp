package com.syncup.syncup_backend.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthFilter(
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        val token = authHeader.removePrefix("Bearer ").trim()
        val userId = jwtService.extractUserId(token)
        val auth = UsernamePasswordAuthenticationToken(userId.toString(), null, emptyList())
        SecurityContextHolder.getContext().authentication = auth
        filterChain.doFilter(request, response)
    }
}
