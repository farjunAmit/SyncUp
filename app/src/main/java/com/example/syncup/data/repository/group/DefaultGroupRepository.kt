package com.example.syncup.data.repository.group

import com.example.syncup.data.dto.AddGroupMemberRequestDto
import com.example.syncup.data.dto.CreateGroupRequestDto
import com.example.syncup.data.model.groups.Group


class DefaultGroupsRepository(
    private val remoteDataSource: GroupRemoteDataSource
) : GroupsRepository {
    override suspend fun getAll(): List<Group> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(userId : Long) : List<Group> {
        return remoteDataSource.getGroups(userId).map { it.toGroup() }
    }

    override suspend fun create(
        name: String,
        invitedEmails: List<String>,
        userId: Long,
    ): Group {
        val body = CreateGroupRequestDto(name, invitedEmails)
        return remoteDataSource.createGroup(userId, body).toGroup()
    }

    override suspend fun rename(id: Long, newName: String) : Group {
        return remoteDataSource.renameGroup(id, newName).toGroup()
    }

    override suspend fun delete(id: Long) {
       remoteDataSource.deleteGroup(id.toLong())
    }

    override suspend fun getGroup(id: Long): Group {
        val groupDto = remoteDataSource.getGroup(id)
        return groupDto.toGroup()
    }

    override suspend fun addMember(groupId: Long, userId: Long) {
        val body = AddGroupMemberRequestDto(userId)
        remoteDataSource.addMember(groupId, body)
    }

    override suspend fun getMemberCount(groupId: Long): Int {
        return remoteDataSource.getGroupSize(groupId)
    }
}