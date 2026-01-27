package com.example.syncup.data.repository.event

import com.example.syncup.data.dto.EventCreateRequestDto
import com.example.syncup.data.dto.EventForVotingDto
import com.example.syncup.data.dto.EventSummaryDto
import com.example.syncup.data.dto.EventTypeCreateRequestDto
import com.example.syncup.data.dto.EventTypeDto
import com.example.syncup.data.dto.SubmitVoteRequestDto
import javax.inject.Inject

class DefaultEventRemoteDataSource @Inject constructor(
    private val eventApi: EventApi
) : EventRemoteDataSource {
    override suspend fun getEvents(groupId: Long): List<EventSummaryDto> =
        eventApi.getEvents(groupId)

    override suspend fun getEvent(eventId: Long): EventForVotingDto = eventApi.getEvent(eventId)

    override suspend fun createEvent(eventCreateRequestDto: EventCreateRequestDto): EventSummaryDto =
        eventApi.createEvent(eventCreateRequestDto)

    override suspend fun deleteEvent(eventId: Long) = eventApi.deleteEvent(eventId)

    override suspend fun submitVotes(submitVoteRequestDto: SubmitVoteRequestDto): EventSummaryDto =
        eventApi.submitVotes(submitVoteRequestDto)

    override suspend fun getEventTypes(groupId: Long): List<EventTypeDto> =
        eventApi.getEventTypes(groupId)

    override suspend fun createEventType(eventCreateDto: EventTypeCreateRequestDto): EventTypeDto =
        eventApi.createEventType(eventCreateDto)


}