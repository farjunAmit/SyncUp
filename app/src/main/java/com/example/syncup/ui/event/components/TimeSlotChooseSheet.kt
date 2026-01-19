package com.example.syncup.ui.event.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
/**
 * Bottom sheet used to select which part of a given day is relevant
 * for an event time slot.
 *
 * The sheet allows choosing:
 * - Morning
 * - Evening
 * - Or both (by selecting both checkboxes)
 *
 * This component is intentionally stateful only for UI interaction
 * (checkbox toggles). The final selection is reported upwards via
 * [onTimeSlotSelected], following state hoisting principles.
 *
 * The sheet itself does not manage event state or persistence.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSlotChooseSheet(
    date: LocalDate,
    onDismiss: () -> Unit,
    initialMorning: Boolean,
    initialEvening: Boolean,
    onTimeSlotSelected: (morning: Boolean, evening: Boolean, date: LocalDate) -> Unit
) {
    var isMorningSelected by remember(date) { mutableStateOf(initialMorning) }
    var isEveningSelected by remember(date) { mutableStateOf(initialEvening) }
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Choose a time slot for $date")
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Morning")
                Checkbox(
                    checked = isMorningSelected,
                    onCheckedChange = { isMorningSelected = it })
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Evening")
                Checkbox(
                    checked = isEveningSelected,
                    onCheckedChange = { isEveningSelected = it })
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { onTimeSlotSelected(isMorningSelected, isEveningSelected, date) }) {
                    Text("Done")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        }
    }
}