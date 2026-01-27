package com.syncup.syncup_backend.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(name = "group_members",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["group_id", "user_id"])
    ]
)
class GroupMemberEntity(
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    var group: GroupEntity,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}