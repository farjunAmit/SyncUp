package com.example.syncup.ui.event.screens

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import com.example.syncup.data.model.events.EventStatus
import com.example.syncup.ui.event.components.EventDetailsContent
import com.example.syncup.ui.event.components.EventUnresolvedContent
import com.example.syncup.ui.event.components.EventVoteContent
import com.example.syncup.ui.event.vm.EventDetailViewModel

/**
 * EventDetailScreen
 *
 * Responsibility:
 * - Displays a single event screen by status.
 * - Loads the event details and (when relevant) the current user's vote draft + vote summary.
 * - Routes the UI to the correct content based on [EventStatus]:
 *   - VOTING      -> [EventVoteContent]
 *   - DECIDED     -> [EventDetailsContent] (read-only details)
 *   - CANCELLED   -> [EventDetailsContent] (read-only details)
 *   - UNRESOLVED  -> Placeholder (future: allow suggesting new time slots / restarting voting)
 *
 * How it works (high level):
 * 1) [EventDetailViewModel] exposes a uiState that contains:
 *    - event: the event details (title, description, status, finalDate, etc.)
 *    - voteDraft: the current user's draft votes (slot -> vote?)
 *    - voteSummary: aggregate vote counts per slot
 * 2) When the screen is opened (LaunchedEffect(eventId)):
 *    - viewModel.loadEventAndVotes(eventId) is called.
 *    - The ViewModel may load voting-related data even if the final UI isn't VOTING.
 *      (This is fine for now; can be optimized later if needed.)
 * 3) The TopAppBar shows the event title (or blank while loading) and a back button.
 * 4) The content is selected using a status-based `when`.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    viewModel: EventDetailViewModel,
    eventId: Long,
    onClick: (Long, Long) -> Unit,
    onBack: () -> Unit,
) {
    /**
     * UI state collected from the ViewModel.
     * - state.event contains the event data to display.
     */
    val state = viewModel.uiState.collectAsState().value
    val event = state.event

    /**
     * Side effect: load event when eventId changes (screen opened / navigated).
     * This ensures the UI has the latest data for this event.
     */
    LaunchedEffect(eventId) {
        viewModel.loadEventAndVotes(eventId)
    }

    /**
     * Scaffold structure:
     * - TopAppBar showing the event title and a back button
     * - Content is rendered based on the event status (voting vs. read-only details, etc.)
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
    ){ innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when (val e = event) {
                null -> Text("Loading...")
                else -> when (e.eventStatus) {
                    EventStatus.VOTING -> {
                        EventVoteContent(
                            event = e,
                            voteDraft = state.voteDraft,
                            voteSummary = state.voteSummary,
                            onVoteChanged = { slot, vote -> viewModel.onVoteChanged(slot, vote) },
                            onSubmit = {
                                viewModel.submitVote()
                                onBack()
                            }
                        )
                    }

                    EventStatus.DECIDED -> {
                        EventDetailsContent(
                            event = e
                        )
                    }

                    EventStatus.CANCELLED -> {
                        EventDetailsContent(
                            event = e
                        )
                    }

                    EventStatus.UNRESOLVED -> {
                        EventUnresolvedContent(
                            event = e,
                            onClink = {
                                onClick(eventId, e.groupId)
                            }
                        )
                    }
                }
            }
        }
    }
}
