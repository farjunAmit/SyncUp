package com.example.syncup.ui.event.uistate
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote
import com.example.syncup.data.model.events.VoteDraft

data class EventVotingUiState(
    val event: Event? = null,
    val voteDraft: VoteDraft? = null
)
