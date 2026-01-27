package com.example.syncup.data.repository.group

import com.example.syncup.data.model.groups.Group

/**
 * GroupsRepository
 *
 * Abstraction layer for accessing and managing groups data.
 * Defines the contract for creating, retrieving, updating and deleting groups.
 *
 * The repository pattern allows the data source implementation
 * (local, remote, database, etc.) to be replaced without affecting
 * the rest of the application.
 */
interface GroupsRepository {

    /**
     * Returns all existing groups.
     */
    suspend fun getAll(): List<Group>


    /**
     * Creates a new group with the given name.
     *
     * @param name The display name of the group.
     * @param invitedEmails Optional list of emails to invite to the group.
     * @return The newly created Group.
     */
    suspend fun create(
        name: String,
        invitedEmails: List<String> = emptyList(),
    ): Group

    /**
     * Renames an existing group.
     *
     * @param id The unique identifier of the group.
     * @param newName The new name to assign to the group.
     */
    suspend fun rename(id: Long, newName: String) : Group

    /**
     * Deletes a group by its identifier.
     *
     * @param id The unique identifier of the group to delete.
     */
    suspend fun delete(id: Long)

    /**
     * Retrieves a specific group by its identifier.
     *
     * @param id The unique identif ier of the group.
     */
    suspend fun getGroup(id: Long) : Group

    /**
     * Adds a member to a group.
     *
     * @param groupId The unique identifier of the group.
     * @param userId The unique identifier of the user to add.
     */
    suspend fun addMember(groupId: Long, userId: Long)

    /**
     * get number of members in a group
     *
     * @param groupId The unique identifier of the group.
     */
    suspend fun getMemberCount(groupId: Long) : Int
}
