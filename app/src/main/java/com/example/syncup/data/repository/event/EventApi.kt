package com.example.syncup.data.repository.event

import com.example.syncup.data.dto.EventCreateRequestDto
import com.example.syncup.data.dto.EventDetailDto
import com.example.syncup.data.dto.EventSummaryDto
import com.example.syncup.data.dto.EventTypeCreateRequestDto
import com.example.syncup.data.dto.EventTypeDto
import com.example.syncup.data.dto.SubmitVoteRequestDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApi {

    @GET("events/{groupId}")
    suspend fun getEvents(@Path("groupId") groupId: Long): List<EventSummaryDto>

    @GET("events/voting/{eventId}")
    suspend fun getEvent(@Path("eventId") eventId: Long): EventDetailDto

    @POST("events/{groupId}")
    suspend fun createEvent(@Path("groupId") groupId: Long, @Body eventCreateRequestDto: EventCreateRequestDto) : EventSummaryDto

    @DELETE("events/{eventId}")
    suspend fun deleteEvent(@Path("eventId") eventId: Long)

    @POST("events/submit-votes")
    suspend fun submitVotes(@Body submitVoteRequestDto: SubmitVoteRequestDto) : EventSummaryDto

    @GET("events/types/{groupId}")
    suspend fun getEventTypes(@Path("groupId") groupId: Long) : List<EventTypeDto>

    @POST("events/types/create")
    suspend fun createEventType(@Body eventCreateDto: EventTypeCreateRequestDto) : EventTypeDto
}