package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.EventCreateRequestDto
import com.syncup.syncup_backend.dto.EventForVotingDto
import com.syncup.syncup_backend.dto.EventSummaryDto
import com.syncup.syncup_backend.dto.EventTypeCreateRequestDto
import com.syncup.syncup_backend.dto.EventTypeDto
import com.syncup.syncup_backend.dto.SubmitVoteRequestDto
import com.syncup.syncup_backend.services.EventService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
class EventController (
    private val eventService: EventService
){
    private fun currentUserId(): Long =
        SecurityContextHolder.getContext().authentication!!.name.toLong()
    @GetMapping("/{groupId}")
    fun getEvents(@PathVariable("groupId") id: Long): List<EventSummaryDto> {
        return eventService.getEvents(id)
    }

    @GetMapping("/event/voting/{eventId}")
    fun getEventVoting(@PathVariable("eventId") eventId: Long): EventForVotingDto {
        return eventService.getEvent(eventId, currentUserId())
    }

    @PostMapping
    fun createEvent(event: EventCreateRequestDto): EventSummaryDto{
        return eventService.createEvent(event)
    }

    @DeleteMapping("/{eventId}")
    fun deleteEvent(@PathVariable("eventId") eventId: Long){
        eventService.deleteEvent(eventId)
    }

    @PostMapping("/submit-votes")
    fun submitVotes(submitVoteRequestDto: SubmitVoteRequestDto) : EventSummaryDto{
        return eventService.submitVotes(submitVoteRequestDto,currentUserId())
    }

    @GetMapping("/types/{groupId}")
    fun getEventTypes(@PathVariable("groupId") groupId: Long): List<EventTypeDto> {
        return eventService.getEventTypes(groupId)
    }

    @PutMapping("types/create")
    fun createEventType(eventCreateDto: EventTypeCreateRequestDto): EventTypeDto {
        return eventService.createEventType(eventCreateDto)
    }
}