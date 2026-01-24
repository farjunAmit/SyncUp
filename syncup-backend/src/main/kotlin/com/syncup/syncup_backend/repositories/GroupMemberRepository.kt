package com.syncup.syncup_backend.repositories

import com.syncup.syncup_backend.entity.GroupMemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GroupMemberRepository : JpaRepository<GroupMemberEntity, Long> {
}