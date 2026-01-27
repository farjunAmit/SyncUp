package com.syncup.syncup_backend.repositories

import com.syncup.syncup_backend.entity.GroupMemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GroupMemberRepository : JpaRepository<GroupMemberEntity, Long>{
    //Counts the number of members in a group
    fun countByGroup_Id(groupId: Long): Int
    fun findByGroup_Id(groupId: Long): List<GroupMemberEntity>
}