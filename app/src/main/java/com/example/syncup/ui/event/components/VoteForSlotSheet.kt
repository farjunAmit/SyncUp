package com.example.syncup.ui.event.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.data.model.events.Vote
/**
 * VoteForSlotSheet
 *
 * Responsibility:
 * - Displays a bottom sheet for voting on a specific [TimeSlot].
 * - Allows the user to pick one vote option (YES / YES_BUT / NO).
 * - Allows clearing the vote for this slot (set to null in the caller).
 * - Allows dismissing without applying changes.
 *
 * How selection works:
 * - The sheet keeps a local state (`vote`) that represents the user's current selection
 *   inside the sheet UI.
 * - When the user presses "Vote", the selected value is sent to the parent via [onVote].
 * - When the user presses "Clear vote", the parent is instructed to clear the vote via [onClear].
 * - "Cancel" simply closes the sheet without changing anything via [onDismiss].
 *
 * @param timeSlot
 * The specific slot the user is voting for (date + part of day).
 *
 * @param currentVote
 * The current vote that the user already has for this slot.
 * - Used to initialize the sheet selection state when the sheet opens.
 * - Note: This parameter is non-null; the parent can map "no vote / cleared" into a default
 *   value for display (e.g. Vote.NO) as done in the screen.
 *
 * @param onDismiss
 * Called when the sheet is dismissed (including "Cancel" button or outside tap).
 *
 * @param onVote
 * Called when the user confirms their selected vote.
 * The parent is responsible for updating the draft/state in the ViewModel.
 *
 * @param onClear
 * Called when the user requests to clear their vote for this slot.
 * The parent typically sets this slot's vote to null in the draft/state.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoteForSlotSheet(
    timeSlot: TimeSlot,
    currentVote: Vote,
    onDismiss: () -> Unit,
    onVote: (vote: Vote) -> Unit,
    onClear: () -> Unit
) {
    /**
     * Available voting options.
     * Uses the enum entries (YES / YES_BUT / NO).
     */
    val options = Vote.entries.toTypedArray()

    /**
     * Local UI state for the currently selected option inside the sheet.
     * Initialized from [currentVote] when the sheet opens.
     */
    var vote by remember { mutableStateOf(currentVote) }

    /**
     * Bottom sheet container:
     * - Dismiss request triggers [onDismiss].
     */
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column {

            /**
             * Basic header describing what the user is voting for.
             */
            Text("Vote for ${timeSlot.date} at ${timeSlot.partOfDay}")
            Spacer(modifier = Modifier.height(16.dp))

            /**
             * Options row:
             * - Each option is shown as a Card containing its label + RadioButton.
             * - Clicking the card or the radio selects the option.
             */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (option in options) {
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1f)
                            .height(100.dp)
                            .clickable { vote = option },
                        border = BorderStroke(1.dp, Color.LightGray)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(option.toString())
                            RadioButton(
                                selected = vote == option,
                                onClick = { vote = option }
                            )
                        }
                    }
                }
            }

            /**
             * Visual separation between options and action buttons.
             */
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )

            /**
             * Action buttons:
             * - Vote: applies the selection and sends it to parent via [onVote]
             * - Clear vote: clears the vote for this slot via [onClear]
             * - Cancel: dismisses without changes via [onDismiss]
             */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { onVote(vote) }
                ) {
                    Text("Vote")
                }
                Button(
                    onClick = onClear
                ) {
                    Text("Clear vote")
                }
                Button(
                    onClick = onDismiss
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}
