package com.example.syncup.ui.event.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote
import com.example.syncup.ui.event.components.EventVoteContent
import com.example.syncup.ui.event.vm.EventVotingViewModel

/**
 * EventDetailScreen
 *
 * Responsibility:
 * - Displays a single event voting screen.
 * - Loads the event details + the current user's existing votes.
 * - Allows the user to select a time slot and set/clear a vote for that slot.
 * - Submits the vote draft and returns back to the previous screen.
 *
 * How it works (high level):
 * 1) ViewModel exposes a uiState that contains:
 *    - event: the event details (title, possible slots, etc.)
 *    - voteDraft: the current user's draft votes for this event (map of slot -> vote?)
 * 2) When the screen is opened (LaunchedEffect(eventId)):
 *    - viewModel.loadEventAndVotes(eventId, user.id) is called.
 * 3) The grid shows all available slots. Clicking a slot opens a bottom sheet to vote.
 * 4) The Submit button submits the vote and navigates back.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    viewModel: EventVotingViewModel,
    eventId: Long,
    onBack: () -> Unit
) {
    /**
     * UI state collected from the ViewModel.
     * - state.event contains the event data to display.
     * - state.voteDraft contains the user's current draft votes.
     */
    val state = viewModel.uiState.collectAsState().value
    val event = state.event
    val voteDraft = state.voteDraft
    val voteSummary = state.voteSummary


    /**
     * Holds the current user's votes for all slots in this event.
     * Default is empty until voteDraft is loaded.
     *
     * Map meaning:
     * - key   = TimeSlot
     * - value = Vote?  (null means "cleared / no selection")
     */
    var userCurrentVote: Map<TimeSlot, Vote?> = emptyMap()

    /**
     * Holds the currently-selected slot.
     * When not null, the voting bottom sheet is displayed.
     */
    var chosenTimeSlot by remember { mutableStateOf<TimeSlot?>(null) }

    /**
     * If a vote draft exists in state, we use it as the source of truth for the grid.
     */
    if (voteDraft.isNotEmpty()) {
        userCurrentVote = voteDraft
    }

    /**
     * Side effect: load event + user's votes when eventId changes (screen opened / navigated).
     * This ensures the UI has the latest data for this event.
     */
    LaunchedEffect(eventId) {
        viewModel.loadEventAndVotes(eventId)
    }

    /**
     * Scaffold structure:
     * - TopAppBar showing the event title and a back button
     * - Content shows:
     *   1) Vote sheet when a slot is selected
     *   2) Slot grid + Submit button
     */
    Scaffold(
        topBar = {
            TopAppBar(
                /**
                 * Displays event title.
                 * event may be null during initial load, so string interpolation is used.
                 */
                title = { Text("${event?.title}") },
                navigationIcon = {
                    IconButton(onClick = onBack)
                    {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {innerPadding ->

        EventVoteContent(
            event = event,
            voteDraft = voteDraft,
            voteSummary = voteSummary,
            onVoteChanged = { slot, vote -> viewModel.onVoteChanged(slot, vote) },
            onSubmit = {
                viewModel.submitVote()
                onBack()
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}