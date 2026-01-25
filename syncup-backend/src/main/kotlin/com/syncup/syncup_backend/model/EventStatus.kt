package com.syncup.syncup_backend.model

enum class EventStatus {
    /** Event is open for voting by participants */
    VOTING,

    /** A final time slot has been chosen */
    DECIDED,

    /** Event was cancelled and is no longer active */
    CANCELLED
}