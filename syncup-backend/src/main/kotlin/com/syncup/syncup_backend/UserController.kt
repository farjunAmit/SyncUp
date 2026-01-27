package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.UserLoginRequest
import com.syncup.syncup_backend.dto.RegisterRequestDto
import com.syncup.syncup_backend.services.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {
}