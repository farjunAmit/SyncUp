package com.syncup.syncup_backend.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.UniqueConstraint


@Entity
@Table(
    name = "event_types",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["group_id", "name"])
    ]
)
class EventTypeEntity(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var color: Long,

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    var group: GroupEntity? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}