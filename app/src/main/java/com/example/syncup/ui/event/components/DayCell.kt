package com.example.syncup.ui.event.components

import androidx.compose.foundation.BorderStroke
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
import com.example.syncup.data.model.events.SlotBlock
import java.time.LocalDate

/**
 * Represents a single selectable day cell inside the event creation calendar.
 *
 * This component is responsible only for:
 * - Displaying a calendar day
 * - Reporting click events upwards
 *
 * The cell does not hold any state and does not decide what happens
 * when it is clicked. All behavior is delegated via [onClick].
 */
@Composable
fun DeyCell(
    date: LocalDate,
    slotToBlock: SlotBlock?,
    isSelected: Boolean = false,
    onClick: (date: LocalDate) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        onClick = {onClick(date)},
        enabled = slotToBlock == null,
        border = if (isSelected) BorderStroke(1.dp, Color.White) else null
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = date.dayOfWeek.toString().substring(0, 2))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = date.dayOfMonth.toString() + "/" + date.monthValue.toString())
            if (slotToBlock != null) {
                Text(text = slotToBlock.reason.toString(),color = Color.Red)
            }
        }
    }
}
