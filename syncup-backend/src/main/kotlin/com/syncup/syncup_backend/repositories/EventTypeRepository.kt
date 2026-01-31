package com.syncup.syncup_backend.repositories

import com.syncup.syncup_backend.entity.EventTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventTypeRepository : JpaRepository<EventTypeEntity, Long> {
    fun findByGroupId(groupId: Long): List<EventTypeEntity>
    fun deleteAllByGroup_Id(groupId: Long)

}