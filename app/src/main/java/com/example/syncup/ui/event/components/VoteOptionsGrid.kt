package com.example.syncup.ui.event.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote

/**
 * VoteOptionsGrid
 *
 * Responsibility:
 * - Displays all available time slots for an event in a grid layout.
 * - Shows the current user's vote state for each slot.
 * - Delegates slot click handling to the parent composable.
 *
 * UI behavior:
 * - Uses a 2-column grid layout.
 * - Each cell represents a single [TimeSlot] via [VoteCell].
 * - Spacing between items is consistent both vertically and horizontally.
 *
 * @param modifier
 * Optional modifier passed from the parent for layout customization.
 *
 * @param userCurrentVote
 * A map of the user's current votes for this event.
 * - Key   = [TimeSlot]
 * - Value = [Vote?]
 * - A null value indicates no vote / cleared state for that slot.
 *
 * @param onSlotClick
 * Callback triggered when a slot cell is clicked.
 * - Typically opens the vote selection bottom sheet for that slot.
 */
@Composable
fun VoteOptionsGrid(
    modifier: Modifier = Modifier,
    userCurrentVote: Map<TimeSlot, Vote?>,
    onSlotClick: (TimeSlot) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),

        // Spacing between rows and columns
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        /**
         * Each grid item represents a single time slot.
         * The map is converted to a list to allow iteration in LazyVerticalGrid.
         */
        items(items = userCurrentVote.toList()) { slot ->
            VoteCell(
                timeSlot = slot.first,   // The time slot being displayed
                vote = slot.second,      // The user's vote for this slot
                onClick = { onSlotClick(slot.first) }
            )
        }
    }
}
