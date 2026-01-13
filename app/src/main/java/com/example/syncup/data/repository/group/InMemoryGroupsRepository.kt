package com.example.syncup.data.repository.group

import com.example.syncup.data.model.Group
import com.example.syncup.data.model.GroupInvite
import java.util.UUID

class InMemoryGroupsRepository : GroupsRepository {
    private val groups = mutableListOf<Group>()
    private val invites = mutableListOf<GroupInvite>()

    init {
        groups.add(Group("1", "Group 1"))
        groups.add(Group("2", "Group 2"))
        groups.add(Group("3", "Group 3"))
    }

    override fun getAll(): List<Group> {
        return groups.toList()
    }

    override suspend fun create(name: String, invitedEmails: List<String>): Group {
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

    override suspend fun rename(id: String, newName: String) {
        val group = groups.find { it.id == id }
        //Todo: check if group exists - if not throw exception
        group?.rename(newName)
    }

    override suspend fun delete(id: String) {
        groups.removeIf { it.id == id }
    }
}
