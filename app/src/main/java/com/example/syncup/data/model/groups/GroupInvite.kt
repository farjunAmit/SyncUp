package com.example.syncup.data.model.groups

/**
 * GroupInvite
 *
 * Data model representing an invitation to join a group.
 * An invite is associated with a specific group and contains
 * information about the invited user(s) and the current status.
 *
 * This model is designed to support future backend integration.
 */
data class GroupInvite(
    val id: String,
    val groupId: String,
    val invitedEmails: String,
    val status: GroupInviteStatus = GroupInviteStatus.PENDING
)

/**
 * GroupInviteStatus
 *
 * Represents the current state of a group invitation.
 */
enum class GroupInviteStatus {

    /** Invitation was sent but not yet answered */
    PENDING,

    /** Invitation was accepted by the invited user */
    ACCEPTED,

    /** Invitation was declined by the invited user */
    REJECTED
}