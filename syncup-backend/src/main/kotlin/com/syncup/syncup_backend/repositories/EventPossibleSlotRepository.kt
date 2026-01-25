package com.syncup.syncup_backend.repositories

import com.syncup.syncup_backend.entity.EventPossibleSlotEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventPossibleSlotRepository : JpaRepository<EventPossibleSlotEntity, Long> {
    fun findByEvent_Id(eventId: Long): List<EventPossibleSlotEntity>
}