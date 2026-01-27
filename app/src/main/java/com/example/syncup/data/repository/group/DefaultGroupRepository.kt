package com.example.syncup.data.repository.group

import android.util.Log
import com.example.syncup.data.dto.AddGroupMemberRequestDto
import com.example.syncup.data.dto.ChangeGroupNameRequestDto
import com.example.syncup.data.dto.CreateGroupRequestDto
import com.example.syncup.data.model.groups.Group
import retrofit2.HttpException
import javax.inject.Inject


class DefaultGroupsRepository @Inject constructor(
    private val remoteDataSource: GroupRemoteDataSource,
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
        try {
            remoteDataSource.deleteGroup(id)
        }catch (e: HttpException) {
            val body = e.response()?.errorBody()?.string()
            Log.e("API", "HTTP ${e.code()} body=$body", e)
        }
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