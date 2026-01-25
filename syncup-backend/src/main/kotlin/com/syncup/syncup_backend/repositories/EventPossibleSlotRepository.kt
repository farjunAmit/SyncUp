package com.syncup.syncup_backend.repositories

import com.syncup.syncup_backend.entity.EventPossibleSlotEntity
import com.syncup.syncup_backend.model.TimeSlot
import org.springframework.data.jpa.repository.JpaRepository

interface EventPossibleSlotRepository : JpaRepository<EventPossibleSlotEntity, Long> {
    fun findByEvent_Id(eventId: Long): List<EventPossibleSlotEntity>

    fun findByEvent_IdAndTimeSlot(eventId: Long, timeSlot: TimeSlot): EventPossibleSlotEntity?
}