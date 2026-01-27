package com.syncup.syncup_backend.entity

import com.syncup.syncup_backend.model.Vote
import jakarta.persistence.*

@Entity
@Table(
    name = "votes",
    uniqueConstraints = [
        UniqueConstraint(
            columnNames = ["event_id", "user_id", "slot_id"]
        )
    ]
)
class VoteEntity(
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    var event: EventEntity? = null,

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    var slot: EventPossibleSlotEntity? = null,

    @Column(name = "user_id", nullable = false)
    var userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    var vote: Vote? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
