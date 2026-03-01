package com.example.syncup.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_types")
data class EventTypeEntity(
    @PrimaryKey
    val id: Long,
    val groupId: Long,
    val type: String,
    val color: Long
)
