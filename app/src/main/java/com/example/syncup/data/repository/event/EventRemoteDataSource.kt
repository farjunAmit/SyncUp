package com.example.syncup.data.repository.event

import com.example.syncup.data.dto.EventCreateRequestDto
import com.example.syncup.data.dto.EventForVotingDto
import com.example.syncup.data.dto.EventSummaryDto
import com.example.syncup.data.dto.EventTypeCreateRequestDto
import com.example.syncup.data.dto.EventTypeDto
import com.example.syncup.data.dto.SubmitVoteRequestDto

interface EventRemoteDataSource {
    suspend fun getEvents (groupId: Long): List<EventSummaryDto>
    suspend fun  getEvent (eventId: Long): EventForVotingDto
    suspend fun createEvent (groupId: Long, eventCreateRequestDto: EventCreateRequestDto) : EventSummaryDto
    suspend fun deleteEvent (eventId: Long)
    suspend fun submitVotes (submitVoteRequestDto: SubmitVoteRequestDto) : EventSummaryDto
    suspend fun getEventTypes (groupId: Long) : List<EventTypeDto>
    suspend fun createEventType (eventCreateDto: EventTypeCreateRequestDto) : EventTypeDto
}