package com.syncup.syncup_backend.entity

import com.syncup.syncup_backend.model.DecisionMode
import jakarta.persistence.Embedded
import com.syncup.syncup_backend.model.EventStatus
import com.syncup.syncup_backend.model.TimeSlot
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated


@Entity
@Table(name = "events")
class EventEntity(
    @Column(nullable = false)
    var groupId: Long,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var description: String,
    @Column(nullable = true)
    @Embedded
    var date: TimeSlot?,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: EventStatus,
    @Column(nullable = true)
    var eventTypeId: Long?,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var decisionMode: DecisionMode
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}