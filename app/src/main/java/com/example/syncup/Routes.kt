package com.example.syncup

/**
 * Routes
 *
 * Central definition of all navigation routes in the application.
 * Contains route constants and helper functions for building
 * parameterized navigation paths.
 *
 * Keeping routes in a single place prevents hardcoded strings
 * and makes navigation easier to maintain.
 */
object Routes {

    /** Route for the main groups list screen */
    const val GROUPS = "groups"

    /** Route pattern for the group details screen */
    const val GROUP_DETAIL = "group/{groupId}"

    const val CREATE_EVENT = "createEvent/{groupId}"

    /**
     * Builds a concrete navigation route for a specific group.
     *
     * @param groupId The unique identifier of the group.
     * @return A navigation route including the groupId parameter.
     */
    fun groupDetail(groupId: String) = "group/$groupId"

    /**
     * Builds a navigation route for creating an event in a specific group.
     *
     * @param groupId The unique identifier of the group.
     * @return A navigation route including the groupId parameter.
     */
    fun createEvent(groupId: String) = "createEvent/$groupId"
}
