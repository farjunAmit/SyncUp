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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoteForSlotSheet(
    onDismiss: () -> Unit,
    onVote: (vote: Vote) -> Unit,
    timeSlot: TimeSlot
) {
    val options = Vote.entries.toTypedArray()
    var vote by remember { mutableStateOf(Vote.NO) }
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column {
            Text("Vote for ${timeSlot.date} at ${timeSlot.partOfDay}")
            Spacer(modifier = Modifier.height(16.dp))
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
                        border = BorderStroke(
                            1.dp, Color.LightGray
                        )
                    )
                    {
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
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )
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
                    onClick = onDismiss
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}