package com.syncup.syncup_backend

import com.syncup.syncup_backend.exceptions.GroupNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GroupExceptionHandler {

    @ExceptionHandler(GroupNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun onGroupNotFoundException(exception: GroupNotFoundException) = mapOf(
        "errorCode" to "GROUP_NOT_FOUND",
        "message" to exception.message
    )
}