package com.example.syncup.data.remote.group

import com.example.syncup.data.dto.AddGroupMemberRequestDto
import com.example.syncup.data.dto.ChangeGroupNameRequestDto
import com.example.syncup.data.dto.CreateGroupRequestDto
import com.example.syncup.data.dto.GroupSummaryDto

interface GroupRemoteDataSource {
    suspend fun getGroups(): List<GroupSummaryDto>

    suspend fun getGroup(groupId: Long): GroupSummaryDto

    suspend fun createGroup(body: CreateGroupRequestDto): GroupSummaryDto
    suspend fun renameGroup(groupId: Long, name: ChangeGroupNameRequestDto): GroupSummaryDto
    suspend fun deleteGroup(groupId: Long)
    suspend fun addMember(groupId: Long, body: AddGroupMemberRequestDto): GroupSummaryDto
    suspend fun getGroupSize(groupId: Long): Int
}