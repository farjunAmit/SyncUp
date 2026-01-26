package com.example.syncup.data.repository.group

import com.example.syncup.data.dto.AddGroupMemberRequestDto
import com.example.syncup.data.dto.CreateGroupRequestDto
import com.example.syncup.data.dto.GroupSummaryDto

interface GroupRemoteDataSource {
    suspend fun getGroups(userId: Long): List<GroupSummaryDto>

    suspend fun getGroup(groupId: Long): GroupSummaryDto

    suspend fun createGroup(userId: Long, body: CreateGroupRequestDto): GroupSummaryDto
    suspend fun renameGroup(groupId: Long, name: String): GroupSummaryDto
    suspend fun deleteGroup(groupId: Long)
    suspend fun addMember(groupId: Long, body: AddGroupMemberRequestDto): GroupSummaryDto
    suspend fun getGroupSize(groupId: Long): Int
}