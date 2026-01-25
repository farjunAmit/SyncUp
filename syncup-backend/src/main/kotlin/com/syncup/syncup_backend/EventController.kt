package com.syncup.syncup_backend

import com.syncup.syncup_backend.dto.EventCreateRequestDto
import com.syncup.syncup_backend.dto.EventForVotingDto
import com.syncup.syncup_backend.dto.EventSummaryDto
import com.syncup.syncup_backend.dto.SubmitVoteRequestDto
import com.syncup.syncup_backend.services.EventService
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
    @GetMapping("/{groupId}")
    fun getEvents(@PathVariable("groupId") id: Long): List<EventSummaryDto> {
        return eventService.getEvents(id)
    }

    @GetMapping("/event/voting/{eventId}/{userId}")
    fun getEventVoting(@PathVariable("eventId") eventId: Long, @PathVariable("userId") userId: Long): EventForVotingDto {
        return eventService.getEvent(eventId, userId)
    }

    @PostMapping
    fun createEvent(event: EventCreateRequestDto): EventSummaryDto{
        return eventService.createEvent(event)
    }

    @DeleteMapping("/{eventId}")
    fun deleteEvent(@PathVariable("eventId") eventId: Long){
        eventService.deleteEvent(eventId)
    }

    @PutMapping
    fun submitVotes(submitVoteRequestDto: SubmitVoteRequestDto){
        eventService.submitVotes(submitVoteRequestDto)
    }
}