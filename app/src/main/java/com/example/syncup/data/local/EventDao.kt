package com.example.syncup.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Upsert
    suspend fun upsertEvent(event: EventEntity)

    @Upsert
    suspend fun upsertEvents(events: List<EventEntity>)

    @Delete
    suspend fun deleteEvent(event: EventEntity)

    @Query("DELETE FROM events WHERE id = :id")
    suspend fun deleteEventById(id: Long)

    @Query("SELECT * FROM events WHERE groupId = :groupId")
    fun getEventsByGroup(groupId: Long): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: Long): Flow<EventEntity?>

    @Query("DELETE FROM events WHERE groupId = :groupId")
    suspend fun clearEventsByGroup(groupId: Long)
    
    @Query("DELETE FROM events WHERE groupId = :groupId AND id NOT IN (:ids)")
    suspend fun deleteEventsNotIn(groupId: Long, ids: List<Long>)

    @Upsert
    suspend fun upsertEventType(type: EventTypeEntity)

    @Upsert
    suspend fun upsertEventTypes(types: List<EventTypeEntity>)

    @Query("SELECT * FROM event_types WHERE groupId = :groupId")
    fun getEventTypesByGroup(groupId: Long): Flow<List<EventTypeEntity>>

    @Query("DELETE FROM event_types WHERE groupId = :groupId")
    suspend fun clearEventTypesByGroup(groupId: Long)
}
