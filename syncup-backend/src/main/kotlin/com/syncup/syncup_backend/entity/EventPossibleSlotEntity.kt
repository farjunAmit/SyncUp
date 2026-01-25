package com.syncup.syncup_backend.entity

import com.syncup.syncup_backend.model.TimeSlot
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "event_possible_slots",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["event_id", "date", "partOfDay"])
    ]
)
class EventPossibleSlotEntity(

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    var event: EventEntity? = null,

    @Embedded

    var timeSlot: TimeSlot? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
