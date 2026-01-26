package com.syncup.syncup_backend.services

import com.syncup.syncup_backend.dto.EventCreateRequestDto
import com.syncup.syncup_backend.dto.EventForVotingDto
import com.syncup.syncup_backend.dto.EventSummaryDto
import com.syncup.syncup_backend.dto.SlotVotingSummaryDto
import com.syncup.syncup_backend.entity.EventPossibleSlotEntity
import com.syncup.syncup_backend.dto.SubmitVoteRequestDto
import com.syncup.syncup_backend.entity.VoteEntity
import com.syncup.syncup_backend.exceptions.EmptyPossibleSlotsException
import com.syncup.syncup_backend.exceptions.EventNotFoundException
import com.syncup.syncup_backend.exceptions.NotValidPossibleSlotException
import com.syncup.syncup_backend.exceptions.PossibleSlotNotFoundException
import com.syncup.syncup_backend.model.DecisionMode
import com.syncup.syncup_backend.model.EventStatus
import com.syncup.syncup_backend.model.Vote
import com.syncup.syncup_backend.projection.SlotVoteSummary
import com.syncup.syncup_backend.repositories.EventPossibleSlotRepository
import com.syncup.syncup_backend.repositories.EventRepository
import com.syncup.syncup_backend.repositories.GroupMemberRepository
import com.syncup.syncup_backend.repositories.VoteRepository
import com.syncup.syncup_backend.toDto
import com.syncup.syncup_backend.toEventDto
import com.syncup.syncup_backend.toEventEntity
import com.syncup.syncup_backend.toModel
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class EventService(
    private val groupMemberRepository: GroupMemberRepository,
    private val eventRepository: EventRepository,
    private val eventPossibleSlotRepository: EventPossibleSlotRepository,
    private val voteRepository: VoteRepository
) {
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
                    timeSlot = requireNotNull(slot.timeSlot).toDto(),
                    votes = votesForSlot,
                    myVote = myVote
                )
            )
        }
        return event.toEventDto(slotVotingList)
    }

    @Transactional
    fun createEvent(event: EventCreateRequestDto): EventSummaryDto {
        if(event.possibleSlots.isEmpty()) {
            throw EmptyPossibleSlotsException(event.name)
        }
        val newEvent = eventRepository.save(event.toEventEntity())
        val possibleSlotsEntities = event.possibleSlots.map { slotDto ->
            EventPossibleSlotEntity(
                event = newEvent,
                timeSlot = slotDto.toModel()
            )
        }
        eventPossibleSlotRepository.saveAll(possibleSlotsEntities)
        return newEvent.toEventDto()
    }

    fun deleteEvent(eventId: Long) {
        if(!eventRepository.existsById(eventId)) {
            throw EventNotFoundException(eventId)
        }
        eventRepository.deleteById(eventId)
    }

    @Transactional
    fun submitVotes(submitVoteRequestDto: SubmitVoteRequestDto) : EventSummaryDto{
        val slots = eventPossibleSlotRepository.findByEvent_Id(submitVoteRequestDto.eventId)
        val slotMap = slots.associateBy { it.timeSlot }
        val event = eventRepository.findById(submitVoteRequestDto.eventId).orElseThrow{
            EventNotFoundException(submitVoteRequestDto.eventId)
        }
        voteRepository.deleteByEvent_IdAndUserId(submitVoteRequestDto.eventId, submitVoteRequestDto.userId)

        val votes = submitVoteRequestDto.votes.map { voteDto ->
            val slotEntity = slotMap[voteDto.timeSlotDto.toModel()]
                ?: throw NotValidPossibleSlotException(submitVoteRequestDto.eventId)

            VoteEntity(
                event = slotEntity.event,
                slot = slotEntity,
                userId = submitVoteRequestDto.userId,
                vote = voteDto.vote
            )
        }
        voteRepository.saveAll(votes)
        val slotsAfterVoting = voteRepository.getSlotSummaries(submitVoteRequestDto.eventId)
        val groupSize = groupMemberRepository.countByGroup_Id(event.groupId)
        val countUser = voteRepository.countDistinctUserIdByEvent_Id(submitVoteRequestDto.eventId)
        if(countUser != groupSize)
            return event.toEventDto()
        var bestSlot : SlotVoteSummary? = null
        if (event.decisionMode == DecisionMode.ALL_OR_NOTHING) {
            for(slotSummary in slotsAfterVoting ) {
                val nullCount =
                    slotSummary.getTotal() -
                            slotSummary.getYesCount() -
                            slotSummary.getYesButCount() -
                            slotSummary.getNoCount()
                if (slotSummary.getYesCount() == groupSize.toLong()) {
                    bestSlot = slotSummary
                    break
                }
                if (slotSummary.getTotal() == groupSize.toLong() && slotSummary.getNoCount()+nullCount == 0L) {
                    if (bestSlot == null || slotSummary.getYesCount() > bestSlot!!.getYesCount()) {
                        bestSlot = slotSummary
                    }
                }
            }
        }

        if (event.decisionMode == DecisionMode.POINTS) {
            slotsAfterVoting.forEach { slotSummary ->
                val slotPoints = slotSummary.getYesCount() * 2 + slotSummary.getYesButCount()
                val bestSlotPoints =
                    bestSlot?.let { it.getYesCount() * 2 + it.getYesButCount() } ?: Long.MIN_VALUE

                if (slotPoints > bestSlotPoints) {
                    bestSlot = slotSummary
                }
            }
        }
        bestSlot?.let {
            val decidedSlotEntity = eventPossibleSlotRepository.findById(it.getSlotId()).orElseThrow {
                PossibleSlotNotFoundException(it.getSlotId())
            }
            if (decidedSlotEntity.event?.id != submitVoteRequestDto.eventId) {
                throw NotValidPossibleSlotException(submitVoteRequestDto.eventId)
            }
            event.date = decidedSlotEntity.timeSlot
            event.status = EventStatus.DECIDED
            eventRepository.save(event)
            return event.toEventDto()
            //Send notification to group members about decided slot (TODO)
        }
        event.status = EventStatus.UNRESOLVED
        eventRepository.save(event)
        return event.toEventDto()
    }
}