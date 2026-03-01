package com.example.syncup.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.syncup.data.model.events.DecisionMode
import com.example.syncup.data.model.events.EventStatus

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey
    val id: Long,
    val groupId: Long,
    val title: String,
    val description: String,
    val status: EventStatus,
    val decisionMode: DecisionMode,
    val eventTypeId: Long?,
    val finalDateJson: String? // Simplified: Store as JSON or use TypeConverter
)
