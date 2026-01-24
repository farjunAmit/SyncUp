package com.syncup.syncup_backend

import com.syncup.syncup_backend.exceptions.UserNotFoundException
import com.syncup.syncup_backend.exceptions.UserNotFoundByEmailException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class, UserNotFoundByEmailException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onUserNotFoundException(exception: UserNotFoundException) = mapOf(
        "errorCode" to "USER_NOT_FOUND",
        "message" to exception.message
    )
}
