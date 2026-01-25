package com.syncup.syncup_backend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import com.syncup.syncup_backend.entity.VoteEntity
import com.syncup.syncup_backend.model.Vote
import com.syncup.syncup_backend.projection.SlotVoteSummary
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface VoteRepository : JpaRepository<VoteEntity, Long> {
    //Counts the number of a specific vote for a slot
    @Query(
        """
    select v.slot.id, v.vote, count(v)
    from VoteEntity v
    where v.event.id = :eventId
    group by v.slot.id, v.vote
    """)
    fun countVotesBySlot(eventId: Long): List<Array<Any>>

    @Query(
        """
    select v.slot.id, v.vote
    from VoteEntity v
    where v.event.id = :eventId and v.userId = :userId
    """)
    fun findMyVotes(eventId: Long, userId: Long): List<Array<Any>>

    fun deleteByEvent_IdAndUserId(eventId: Long, userId: Long)

    @Query("""
    select 
      v.slot.id as slotId,
      count(v.id) as total,
      sum(case when v.vote = com.syncup.syncup_backend.model.Vote.YES then 1 else 0 end) as yesCount,
      sum(case when v.vote = com.syncup.syncup_backend.model.Vote.YES_BUT then 1 else 0 end) as yesButCount,
      sum(case when v.vote = com.syncup.syncup_backend.model.Vote.NO then 1 else 0 end) as noCount
    from VoteEntity v
    where v.event.id = :eventId
    group by v.slot.id
""")
    fun getSlotSummaries(@Param("eventId") eventId: Long): List<SlotVoteSummary>

    //Counts the number of distinct users who voted in an event
    fun countDistinctUserIdByEvent_Id(eventId: Long): Int
}