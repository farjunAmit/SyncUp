package com.syncup.syncup_backend

import com.syncup.syncup_backend.exceptions.InvalidPasswordOrEmailException
import com.syncup.syncup_backend.exceptions.UserAlreadyExistsException
import com.syncup.syncup_backend.exceptions.UserNotFoundException
import com.syncup.syncup_backend.exceptions.UserNotFoundByEmailException
import com.syncup.syncup_backend.exceptions.UserRegisterFailedException
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

    @ExceptionHandler(InvalidPasswordOrEmailException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun onInvalidPasswordOrEmailException(exception: InvalidPasswordOrEmailException) = mapOf(
        "errorCode" to "INVALID_CREDENTIALS",
        "message" to exception.message
    )

    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onUserAlreadyExistsException(exception: UserAlreadyExistsException) = mapOf(
        "errorCode" to "USER_ALREADY_EXISTS",
        "message" to exception.message
    )

    @ExceptionHandler(UserRegisterFailedException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun onUserRegisterFailedException(exception: UserRegisterFailedException) = mapOf(
        "errorCode" to "USER_REGISTER_FAILED",
        "message" to exception.message
    )

}
