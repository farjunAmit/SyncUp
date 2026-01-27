package com.example.syncup.data.repository.group

import com.example.syncup.data.dto.AddGroupMemberRequestDto
import com.example.syncup.data.dto.ChangeGroupNameRequestDto
import com.example.syncup.data.dto.CreateGroupRequestDto
import com.example.syncup.data.model.groups.Group
import com.example.syncup.data.session.SessionStore
import javax.inject.Inject


class DefaultGroupsRepository @Inject constructor(
    private val remoteDataSource: GroupRemoteDataSource,
    private val sessionStore: SessionStore
) : GroupsRepository {

    override suspend fun getAll(): List<Group> {
        return remoteDataSource.getGroups().map { it.toGroup() }
    }

    override suspend fun create(
        name: String,
        invitedEmails: List<String>,
    ): Group {
        val body = CreateGroupRequestDto(name, invitedEmails)
        return remoteDataSource.createGroup(body).toGroup()
    }

    override suspend fun rename(id: Long, newName: String): Group {
        return remoteDataSource.renameGroup(id, ChangeGroupNameRequestDto(newName)).toGroup()
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