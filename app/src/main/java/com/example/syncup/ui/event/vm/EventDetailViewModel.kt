package com.example.syncup.ui.event.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote
import com.example.syncup.data.repository.event.EventRepository
import com.example.syncup.ui.event.uistate.EventDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

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
@HiltViewModel
class EventDetailViewModel @Inject constructor(private val eventRepo: EventRepository) : ViewModel() {

    /**
     * Internal mutable UI state.
     * Exposed as an immutable [StateFlow] to the UI.
     */
    private val _uiState = MutableStateFlow(EventDetailUiState())

    /**
     * Public UI state observed by the composable.
     */
    val uiState: StateFlow<EventDetailUiState> = _uiState.asStateFlow()

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
     */

    fun loadEventAndVotes(eventId: Long) {
        viewModelScope.launch {
            val event = eventRepo.getById(eventId)
            val prevVote = event?.myVotes ?: emptyMap()
            val voteSummary = event?.slotCounts ?: emptyMap()

            _uiState.update { current ->
                current.copy(
                    event = event,
                    voteDraft = prevVote,
                    voteSummary = voteSummary
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

            val oldVoteDraft = current.voteDraft
            val oldVote = oldVoteDraft[timeSlot]

            val newVoteDraft = oldVoteDraft + (timeSlot to vote)

            val oldSummaryForSlot =
                current.voteSummary?.get(timeSlot) ?: emptyMap()

            val newSummaryForSlot = oldSummaryForSlot.toMutableMap()

            if (oldVote != null) {
                val oldCount = newSummaryForSlot[oldVote] ?: 0
                newSummaryForSlot[oldVote] = (oldCount - 1).coerceAtLeast(0)
            }

            if (vote != null) {
                val newCount = newSummaryForSlot[vote] ?: 0
                newSummaryForSlot[vote] = newCount + 1
            }

            val newVoteSummary =
                current.voteSummary
                    ?.toMutableMap()
                    ?.apply { put(timeSlot, newSummaryForSlot) }

            current.copy(
                voteDraft = newVoteDraft,
                voteSummary = newVoteSummary
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
            val voteDraft = _uiState.value.voteDraft
            val event = _uiState.value.event ?: return@launch
            eventRepo.submitVote(event.id, voteDraft)
        }
    }

}
