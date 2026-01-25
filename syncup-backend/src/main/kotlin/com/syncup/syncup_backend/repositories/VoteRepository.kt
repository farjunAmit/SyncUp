package com.syncup.syncup_backend.repositories

import org.springframework.data.jpa.repository.JpaRepository
import com.syncup.syncup_backend.entity.VoteEntity
import com.syncup.syncup_backend.model.Vote
import org.springframework.data.jpa.repository.Query

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

}