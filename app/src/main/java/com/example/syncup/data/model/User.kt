package com.example.syncup.data.model
/**
 * Represents a user in the system.
 *
 * This model describes a real person who can:
 * - Be a member of one or more groups
 * - Participate in events
 * - Submit votes for time slots
 */
data class User(
    val id: String,
    val displayName: String,
    val email: String
)
