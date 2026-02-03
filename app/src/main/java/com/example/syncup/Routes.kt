package com.example.syncup

/**
 * Routes
 *
 * Central definition of all navigation routes in the application.
 * All IDs are Long to keep navigation type-safe and consistent.
 */
object Routes {
    const val GATE = "gate"
    const val LOGIN = "login"

    const val GROUPS = "groups"
    const val GROUP_DETAIL = "groupDetail/{groupId}"
    const val CREATE_EVENT = "createEvent/{groupId}"

    const val EDIT_EVENT = "editEvent/{eventId}/{groupId}"
    const val EVENT_DETAIL = "eventDetail/{eventId}"

    fun groupDetail(groupId: Long) = "groupDetail/$groupId"
    fun createEvent(groupId: Long) = "createEvent/$groupId"
    fun eventDetail(eventId: Long) = "eventDetail/$eventId"
    fun editEvent(eventId: Long, groupId: Long) = "editEvent/$eventId/$groupId"
}

