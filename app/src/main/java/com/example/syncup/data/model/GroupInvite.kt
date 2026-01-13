package com.example.syncup.data.model

data class GroupInvite(
    val id: String,
    val groupId: String,
    val invitedEmails: String,
    val status: GroupInviteStatus = GroupInviteStatus.PENDING
)

enum class GroupInviteStatus {
    PENDING,
    ACCEPTED,
    REJECTED
}