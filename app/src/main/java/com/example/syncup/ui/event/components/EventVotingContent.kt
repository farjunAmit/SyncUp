package com.example.syncup.ui.event.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote

@Composable
fun EventVoteContent(
    event: Event?,
    voteDraft: Map<TimeSlot, Vote?>,
    voteSummary: Map<TimeSlot, Map<Vote, Int>>?,
    onVoteChanged: (TimeSlot, Vote?) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    var chosenTimeSlot by remember { mutableStateOf<TimeSlot?>(null) }

    val userCurrentVote = if (voteDraft.isNotEmpty()) voteDraft else emptyMap()

    chosenTimeSlot?.let { slot ->
        VoteForSlotSheet(
            timeSlot = slot,
            currentVote = userCurrentVote[slot] ?: Vote.NO,
            onDismiss = { chosenTimeSlot = null },
            onVote = { vote ->
                onVoteChanged(slot, vote)
                chosenTimeSlot = null
            },
            onClear = {
                onVoteChanged(slot, null)
                chosenTimeSlot = null
            }
        )
    }

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        if (event == null) {
            Text("Loading...")
            return@Column
        }

        VoteOptionsGrid(
            userCurrentVote = userCurrentVote,
            voteSummary = voteSummary,
            onSlotClick = { chosenTimeSlot = it },
        )

        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = onSubmit
        ) {
            Text("Submit")
        }
    }
}
