package com.example.syncup.data.repository.group

import com.example.syncup.data.model.groups.Group
import com.example.syncup.data.model.groups.GroupInvite
import java.util.UUID

/**
 * InMemoryGroupsRepository
 *
 * In-memory implementation of GroupsRepository.
 * Used for development and prototyping purposes.
 *
 * Stores groups and invitations in local mutable collections.
 * This implementation is not persistent and will reset on app restart.
 */
class InMemoryGroupsRepository : GroupsRepository {

    private val groups = mutableListOf<Group>()
    private val invites = mutableListOf<GroupInvite>()

    init {
        // Temporary mock data for initial UI development
        groups.add(Group("1", "Group 1"))
        groups.add(Group("2", "Group 2"))
        groups.add(Group("3", "Group 3"))
    }

    /**
     * Returns a copy of the current groups list.
     * A defensive copy is used to prevent external mutation.
     */
    override suspend fun getAll(): List<Group> {
        return groups.toList()
    }

    /**
     * Creates a new group and optional invitations.
     *
     * A unique ID is generated for the group and for each invite.
     */
    override suspend fun create(
        name: String,
        invitedEmails: List<String>
    ): Group {
        val groupId = UUID.randomUUID().toString()
        val newGroup = Group(groupId, name)
        groups.add(newGroup)

        invitedEmails.forEach { email ->
            val inviteId = UUID.randomUUID().toString()
            val invite = GroupInvite(inviteId, groupId, email)
            invites.add(invite)
        }

        return newGroup
    }

    /**
     * Updates the name of an existing group.
     *
     * If the group is not found, the operation is silently ignored.
     * This behavior may be replaced with error handling in the future.
     */
    override suspend fun rename(id: String, newName: String) {
        val group = groups.find { it.id == id }
        group?.rename(newName)
    }

    /**
     * Removes a group by its identifier.
     *
     * Associated invitations are not removed in this implementation.
     */
    override suspend fun delete(id: String) {
        groups.removeIf { it.id == id }
    }

    /**
     * Retrieves a specific group by its identifier.
     *
     * This implementation is not optimized for performance.
     */
    override suspend fun getGroup(id: String): Group? {
        return groups.find { it.id == id }
    }
}
