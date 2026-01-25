package com.syncup.syncup_backend

import com.syncup.syncup_backend.exceptions.EventNotFoundException
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
}