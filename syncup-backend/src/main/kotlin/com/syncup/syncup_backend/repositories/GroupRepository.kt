package com.syncup.syncup_backend.repositories

import com.syncup.syncup_backend.entity.GroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GroupRepository : JpaRepository<GroupEntity, Long> {
    @Query(
        "SELECT g " +
                "FROM GroupEntity g JOIN GroupMemberEntity m " +
                "ON g = m.group " +
                "WHERE m.user.id = :userId"
    )
    fun findUserGroups(userId: Long): List<GroupEntity>
}