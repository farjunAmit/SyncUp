package com.syncup.syncup_backend.services

import com.syncup.syncup_backend.dto.EventForVotingDto
import com.syncup.syncup_backend.dto.EventSummaryDto
import com.syncup.syncup_backend.dto.SlotVotingSummaryDto
import com.syncup.syncup_backend.exceptions.EventNotFoundException
import com.syncup.syncup_backend.model.Vote
import com.syncup.syncup_backend.repositories.EventPossibleSlotRepository
import com.syncup.syncup_backend.repositories.EventRepository
import com.syncup.syncup_backend.repositories.VoteRepository
import com.syncup.syncup_backend.toEventDto
import org.springframework.stereotype.Service

@Service
class EventService (
    private val eventRepository: EventRepository,
    private val eventPossibleSlotRepository: EventPossibleSlotRepository,
    private val voteRepository: VoteRepository
){
    fun getEvents(groupId: Long): List<EventSummaryDto> {
        return eventRepository.findByGroupId(groupId).map { it.toEventDto() }
    }

    fun getEvent(eventId: Long, userId: Long): EventForVotingDto {
        val event = eventRepository.findById(eventId)
            .orElseThrow { EventNotFoundException(eventId) }

        val slots = eventPossibleSlotRepository.findByEvent_Id(eventId)

        val countVotes = voteRepository.countVotesBySlot(eventId)
        val myVotes = voteRepository.findMyVotes(eventId, userId)

        // 1) Map: slotId -> (Vote -> count)
        val countsBySlotId = mutableMapOf<Long, MutableMap<Vote, Int>>()
        countVotes.forEach { row ->
            val slotId = row[0] as Long
            val vote = row[1] as Vote
            val cnt = (row[2] as Long).toInt()

            val voteMap = countsBySlotId.getOrPut(slotId) {
                mutableMapOf(Vote.YES to 0, Vote.YES_BUT to 0, Vote.NO to 0)
            }
            voteMap[vote] = cnt
        }

        // 2) Map: slotId -> myVote
        val myVoteBySlotId = mutableMapOf<Long, Vote>()
        myVotes.forEach { row ->
            val slotId = row[0] as Long
            val vote = row[1] as Vote
            myVoteBySlotId[slotId] = vote
        }

        // 3) Build DTO list per slot
        val slotVotingList = mutableListOf<SlotVotingSummaryDto>()
        slots.forEach { slot ->
            val slotId = slot.id
            val votesForSlot = (countsBySlotId[slotId] ?: mutableMapOf(
                Vote.YES to 0, Vote.YES_BUT to 0, Vote.NO to 0
            ))
            val myVote = myVoteBySlotId[slotId]
            slotVotingList.add(
                SlotVotingSummaryDto(
                    timeSlot = requireNotNull(slot.timeSlot),
                    votes = votesForSlot,
                    myVote = myVote
                )
            )
        }
        return event.toEventDto(slotVotingList)
    }
}