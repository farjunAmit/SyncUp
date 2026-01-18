package com.example.syncup.ui.event.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    onClick: (date: LocalDate) -> Unit
){
    Column (modifier = Modifier.clickable(onClick = {onClick(date)})){
        //Placeholder for now
    }
}
