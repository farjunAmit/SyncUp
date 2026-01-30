package com.example.syncup.ui.event.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote

/**
 * VoteCell
 *
 * Responsibility:
 * - Displays a single selectable time slot in the voting grid.
 * - Visually represents the user's current vote for that slot.
 * - Acts as an entry point to open the vote selection UI (bottom sheet).
 *
 * Visual behavior:
 * - The entire card is clickable.
 * - The border color reflects the user's vote:
 *   - Vote.YES     -> Green border (user can attend)
 *   - Vote.YES_BUT -> Yellow border (user prefers not to, but can attend)
 *   - Vote.NO      -> Red border (user cannot attend)
 *   - null         -> No border (no vote / cleared state)
 *
 * @param timeSlot
 * The time slot represented by this cell (date + part of day).
 *
 * @param vote
 * The current user's vote for this slot.
 * - null means the user has not selected any option for this slot.
 *
 * @param onClick
 * Callback triggered when the cell is clicked.
 * - Typically opens a bottom sheet or dialog allowing the user to vote
 *   for this specific slot.
 */
@Composable
fun VoteCell(
    timeSlot: TimeSlot,
    vote: Vote?,
    voteSummaryForSlot: Map<Vote, Int>?,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),

        // Clicking the card triggers slot selection
        onClick = onClick,

        /**
         * Border color is used as a lightweight visual indicator
         * for the user's current vote.
         *
         * Using border (instead of background) keeps the UI readable
         * and allows easy extension (e.g. count indicators later).
         */
        border = when (vote) {
            Vote.YES -> BorderStroke(1.dp, Color.Green)
            Vote.YES_BUT -> BorderStroke(1.dp, Color.Yellow)
            Vote.NO -> BorderStroke(1.dp, Color.Red)
            else -> null
        }
    ) {
        /**
         * Slot content:
         * - Day of week
         * - Date (day/month)
         * - Part of day (e.g. morning / evening)
         *
         * Content is centered to make each slot visually balanced
         * within a grid layout.
         */
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = timeSlot.date.dayOfWeek.toString())
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = timeSlot.date.dayOfMonth.toString() +
                        "/" +
                        timeSlot.date.monthValue.toString()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = timeSlot.partOfDay.toString())
            Spacer(modifier = Modifier.height(16.dp))
            VotingProgress(
                voteSummaryForSlot = voteSummaryForSlot
            )
        }
    }
}
