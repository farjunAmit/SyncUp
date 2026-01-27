package com.example.syncup.ui.event.uistate
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote

/**
 * EventVotingUiState
 *
 * Represents the complete UI state for the event voting screen.
 *
 * This state is exposed by [EventVotingViewModel] and collected by the UI.
 * It contains all the data needed to render the event details and
 * the current user's voting progress.
 *
 * @property event
 * The event being displayed.
 * - Contains event metadata such as title, possible time slots,
 *   decision mode, and current status.
 * - Null while the event is still loading or not found.
 *
 * @property voteDraft
 * The current user's in-progress vote data for this event.
 * - Maps each [TimeSlot] to a [Vote?].
 * - A null value means the draft has not been loaded yet.
 * - A null vote value inside the map represents a cleared / unselected vote.
 *
 * Lifecycle:
 * - Initially both values are null.
 * - After loading, `event` is set first.
 * - `voteDraft` is set once the user's votes are retrieved or initialized.
 */
data class EventVotingUiState(
    val event: Event? = null,
    val voteDraft: Map<TimeSlot, Vote?> = emptyMap()
)
