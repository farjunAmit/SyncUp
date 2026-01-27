package com.syncup.syncup_backend.repositories

import com.syncup.syncup_backend.entity.EventEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<EventEntity, Long> {
    fun findByGroupId(groupId: Long): List<EventEntity>
}