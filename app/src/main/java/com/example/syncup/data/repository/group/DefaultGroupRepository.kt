package com.example.syncup.data.repository.group

import android.util.Log
import com.example.syncup.data.dto.AddGroupMemberRequestDto
import com.example.syncup.data.dto.ChangeGroupNameRequestDto
import com.example.syncup.data.dto.CreateGroupRequestDto
import com.example.syncup.data.local.GroupDao
import com.example.syncup.data.mapper.toGroup
import com.example.syncup.data.mapper.toGroupEntity
import com.example.syncup.data.model.groups.Group
import com.example.syncup.data.remote.group.GroupRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject


class DefaultGroupRepository @Inject constructor(
    private val remoteDataSource: GroupRemoteDataSource,
    private val groupDao: GroupDao
) : GroupsRepository {

    override suspend fun getAll(): List<Group> {
        return remoteDataSource.getGroups().map { it.toGroup() }
    }

    override suspend fun create(
        name: String,
        invitedEmails: List<String>,
    ): Group {
        val body = CreateGroupRequestDto(name, invitedEmails)
        val groupEntity = remoteDataSource.createGroup(body).toGroupEntity()
        groupDao.upsertGroup(groupEntity)
        return groupEntity.toGroup()
    }

    override suspend fun rename(id: Long, newName: String): Group {
        val groupEntity = groupDao.getGroupById(id).first()
        groupEntity?.let { old ->
            val updated = old.copy(name = newName)
            groupDao.upsertGroup(updated)
        }
        return remoteDataSource
            .renameGroup(id, ChangeGroupNameRequestDto(newName))
            .toGroup()
    }


    override suspend fun delete(id: Long) {
        remoteDataSource.deleteGroup(id)
        groupDao.deleteGroup(id)
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

    override fun observeAll(): Flow<List<Group>> {
        return groupDao.getGroups().map { entity -> entity.map { it.toGroup() } }
    }

    override suspend fun refresh() {
        val groups = remoteDataSource.getGroups().map { it.toGroupEntity() }
        if(groups.isEmpty()){
            groupDao.clear()
        }
        else {
            groupDao.deleteGroupsNotIn(groups.map { it.id })
            groupDao.upsertGroups(groups)
        }
    }
}