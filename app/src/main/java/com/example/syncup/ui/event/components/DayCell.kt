package com.example.syncup.ui.event.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.SlotBlock
import com.example.syncup.ui.event.utils.LocalShowMessage
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
fun DayCell(
    date: LocalDate,
    slotToBlock: SlotBlock?,
    isSelected: Boolean = false,
    onClick: (date: LocalDate) -> Unit,
) {
    val msg = slotToBlock?.reason?.toString() ?: ""
    val showMessage = LocalShowMessage.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .alpha(if (slotToBlock != null) 0.5f else 1f),
        onClick = { if(slotToBlock == null) onClick(date) else showMessage(msg)},
        border = if (isSelected) BorderStroke(1.dp, Color.White) else null
    ) {
        val contentColor = if (slotToBlock != null) Color.Red else LocalContentColor.current
        CompositionLocalProvider(
            LocalContentColor provides contentColor
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
            }
        }
    }
}
