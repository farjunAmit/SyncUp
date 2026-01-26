package com.example.syncup.ui.event.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote
import com.example.syncup.data.model.events.VoteDraft
import com.example.syncup.data.repository.event.EventRepository
import com.example.syncup.data.repository.group.GroupsRepository
import com.example.syncup.ui.event.uistate.EventVotingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * EventVotingViewModel
 *
 * Responsibility:
 * - Acts as the state holder and coordinator for the event voting screen.
 * - Loads event data and the current user's existing votes.
 * - Manages an in-progress vote draft while the user interacts with the UI.
 * - Submits the final vote draft to the [EventRepository].
 *
 * This ViewModel does NOT:
 * - Decide how an event is finalized (that logic lives in the repository/domain layer).
 * - Handle navigation or UI rendering logic.
 */
class EventVotingViewModel(private val eventRepo: EventRepository, private val groupRepo: GroupsRepository) : ViewModel() {

    /**
     * Internal mutable UI state.
     * Exposed as an immutable [StateFlow] to the UI.
     */
    private val _uiState = MutableStateFlow(EventVotingUiState())

    /**
     * Public UI state observed by the composable.
     */
    val uiState: StateFlow<EventVotingUiState> = _uiState.asStateFlow()

    /**
     * Loads the event details and the current user's existing votes.
     *
     * Flow:
     * 1) Fetch the event from the repository.
     * 2) Retrieve all possible slots from the event.
     * 3) Retrieve the user's previous votes (if any).
     * 4) Create a [VoteDraft] that:
     *    - Contains all possible slots.
     *    - Pre-fills votes where the user already voted.
     * 5) Update the UI state with the loaded event and draft.
     *
     * @param eventId The ID of the event to load.
     * @param userId  The ID of the current user.
     */
    fun loadEventAndVotes(eventId: Long, userId: Long) {
        viewModelScope.launch {
            val event = eventRepo.getById(eventId)

            val possibleSlots = event?.possibleSlots ?: emptySet()
            val prevVote = event?.getVoteForUser(userId) ?: emptyMap()

            val voteDraft = createVoteDraft(possibleSlots, prevVote, userId)

            _uiState.update { current ->
                current.copy(
                    event = event,
                    voteDraft = voteDraft
                )
            }
        }
    }

    /**
     * Updates the current vote draft when the user changes a vote for a specific slot.
     *
     * Behavior:
     * - If no draft exists, the update is ignored.
     * - A new votes map is created (immutability).
     * - A null vote represents a cleared / unselected slot.
     *
     * @param timeSlot The slot being updated.
     * @param vote     The new vote value, or null to clear the vote.
     */
    fun onVoteChanged(timeSlot: TimeSlot, vote: Vote?) {
        _uiState.update { current ->
            val oldVote = current.voteDraft ?: return@update current

            val newVotes = oldVote.votes + (timeSlot to vote)

            current.copy(
                voteDraft = oldVote.copy(votes = newVotes)
            )
        }
    }

    /**
     * Submits the current vote draft to the repository.
     *
     * Behavior:
     * - If either the event or the vote draft is missing, submission is skipped.
     * - The repository is responsible for:
     *   - Persisting the vote
     *   - Checking if all members have voted
     *   - Finalizing the event if applicable
     *
     * Note:
     * - The ViewModel does not update the UI state after submission.
     * - Navigation and result handling are managed by the UI layer.
     */
    fun submitVote() {
        viewModelScope.launch {
            val voteDraft = _uiState.value.voteDraft ?: return@launch
            val event = _uiState.value.event ?: return@launch
            val memberCount = groupRepo.getMemberCount(event.groupId)
            eventRepo.submitVote(event.id, voteDraft, memberCount)
        }
    }

    /**
     * Creates a new [VoteDraft] for the given user.
     *
     * Purpose:
     * - Ensures that all possible slots exist in the draft.
     * - Preserves previously submitted votes where available.
     *
     * @param possibleSlots All slots that can be voted on in the event.
     * @param prevVote      The user's previous votes (may be empty).
     * @param userId        The ID of the user owning this draft.
     *
     * @return A fully-initialized [VoteDraft] ready for editing in the UI.
     */
    private fun createVoteDraft(
        possibleSlots: Set<TimeSlot>,
        prevVote: Map<TimeSlot, Vote?>,
        userId: Long
    ): VoteDraft {
        val votes = mutableMapOf<TimeSlot, Vote?>()

        for (slot in possibleSlots) {
            votes[slot] = prevVote[slot]
        }

        return VoteDraft(
            userId = userId,
            votes = votes.toMap()
        )
    }
}
