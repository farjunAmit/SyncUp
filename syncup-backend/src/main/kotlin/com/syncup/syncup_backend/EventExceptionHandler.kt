package com.syncup.syncup_backend

import com.syncup.syncup_backend.exceptions.EventNotFoundException
import com.syncup.syncup_backend.exceptions.PossibleSlotNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class EventExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEventNotFoundException(ex: EventNotFoundException) = mapOf(
        "errorCode" to "EVENT_NOT_FOUND",
        "message" to ex.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(ex: IllegalArgumentException) = mapOf(
        "errorCode" to "ILLEGAL_ARGUMENT",
        "message" to ex.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleGenericException(ex: Exception) = mapOf(
        "errorCode" to "ILLEGAL_ARGUMENT",
        "message" to ex.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEventSlotConflictException(ex : PossibleSlotNotFoundException) = mapOf(
        "errorCode" to "EVENT_SLOT_CONFLICT",
        "message" to ex.message
    )

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun handleEventFullException(ex : IllegalStateException) = mapOf(
        "errorCode" to "PASSWORD_HASH_FAILED",
        "message" to ex.message
    )
}