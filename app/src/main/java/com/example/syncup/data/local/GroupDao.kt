package com.example.syncup.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Upsert
    suspend fun upsertGroup(group: GroupEntity)

    @Upsert
    suspend fun upsertGroups(groups: List<GroupEntity>)

    @Delete
    suspend fun deleteGroup(group: GroupEntity)

    @Query("DELETE FROM `groups` WHERE id = :id")
    suspend fun deleteGroup(id: Long)
    @Query("DELETE FROM `groups` WHERE id NOT IN (:ids)")
    suspend fun deleteGroupsNotIn(ids: List<Long>)

    @Query("SELECT * FROM `groups`")
    fun getGroups(): Flow<List<GroupEntity>>

    @Query("SELECT * FROM `groups` WHERE id = :id")
    fun getGroupById(id: Long): Flow<GroupEntity?>

    @Query("DELETE FROM `groups`")
    suspend fun clear()
}
