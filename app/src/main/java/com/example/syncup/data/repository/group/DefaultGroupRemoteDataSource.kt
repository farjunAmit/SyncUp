package com.example.syncup.data.repository.group

import com.example.syncup.data.dto.AddGroupMemberRequestDto
import com.example.syncup.data.dto.CreateGroupRequestDto
import javax.inject.Inject

class DefaultGroupRemoteDataSource @Inject constructor(
    private val api: GroupApi
) : GroupRemoteDataSource {

    override suspend fun getGroups(userId: Long) = api.getGroups(userId)

    override suspend fun getGroup(groupId: Long) = api.getGroup(groupId)

    override suspend fun createGroup(userId: Long, body: CreateGroupRequestDto) =
        api.createGroup(userId, body)

    override suspend fun renameGroup(groupId: Long, name: String) =
        api.renameGroup(groupId, name)

    override suspend fun deleteGroup(groupId: Long) = api.deleteGroup(groupId)

    override suspend fun addMember(groupId: Long, body: AddGroupMemberRequestDto) =
        api.addMember(groupId, body)

    override suspend fun getGroupSize(groupId: Long) = api.getGroupSize(groupId)
}
