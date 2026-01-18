package com.example.syncup.ui.event.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate

/**
 * Calendar component used during event creation to select possible event dates.
 *
 * The calendar displays a fixed window of consecutive days (currently two weeks)
 * arranged in a grid layout. Each cell represents a single calendar day.
 *
 * Responsibilities:
 * - Generate a list of dates based on the current window start.
 * - Display the dates in a grid.
 * - Report day selection events upwards via [onCellClick].
 *
 * This component does not manage any event state and does not decide
 * what happens when a date is selected.
 */
@Composable
fun CreateEventCalendar(
    modifier: Modifier = Modifier,
    onCellClick: (date: LocalDate) -> Unit,
) {
    val windowDays: Int = 14
    var windowStart by remember { mutableStateOf(LocalDate.now()) }
    //val windowWnd = windowStart.plusDays(windowDays)
    val datesList = remember(windowStart) {
        buildDates(windowStart, windowDays)
    }

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(7),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = datesList) { date ->
            DeyCell(
                date = date,
                onClick = onCellClick
            )
        }
    }
}

fun buildDates(windowStart: LocalDate, windowDays: Int): List<LocalDate> {
    val datesList = mutableListOf<LocalDate>()
    for (i in 0 until windowDays) {
        datesList.add(windowStart.plusDays(i.toLong()))
    }
    return datesList
}
