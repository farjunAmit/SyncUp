package com.example.syncup.ui.event.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import com.example.syncup.data.model.events.PartOfDay
import com.example.syncup.data.model.events.SlotBlock
import com.example.syncup.data.model.events.TimeSlot

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
    isSelected: (date: LocalDate) -> Boolean,
    slotsToBlock: MutableMap<TimeSlot, SlotBlock>
) {
    val windowDays: Int = 21
    var windowStart by remember { mutableStateOf(LocalDate.now()) }
    //val windowWnd = windowStart.plusDays(windowDays)
    val datesList = remember(windowStart) {
        buildDates(windowStart, windowDays)
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { windowStart = max(windowStart.minusWeeks(2), LocalDate.now()) },
                enabled = windowStart != LocalDate.now()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                    contentDescription = "Previous week"
                )
            }
            IconButton(
                onClick = { windowStart = startOfWeekSunday(windowStart.plusWeeks(2)) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = "Next week"
                )
            }
        }
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = datesList) { date ->
                var slotToBlock: SlotBlock? = null
                if (date != null) {
                    val timeSlotOption1 = TimeSlot(date, PartOfDay.MORNING)
                    val timeSlotOption2 = TimeSlot(date, PartOfDay.EVENING)
                    if (slotsToBlock.containsKey(timeSlotOption1)) {
                        slotToBlock = slotsToBlock[timeSlotOption1]
                    } else if (slotsToBlock.containsKey(timeSlotOption2)) {
                        slotToBlock = slotsToBlock[timeSlotOption2]

                    }
                    DeyCell(
                        date = date,
                        isSelected = isSelected(date),
                        slotToBlock = slotToBlock,
                        onClick = onCellClick
                    )
                }
            }
        }
    }
}

fun buildDates(windowStart: LocalDate, windowDays: Int): List<LocalDate?> {
    val datesList = mutableListOf<LocalDate?>()
    val daysBeforeSunday = windowStart.dayOfWeek.value % 7

    for (i in 0 until windowDays) {
        if (i < daysBeforeSunday) {
            datesList.add(null)
            continue
        }
        datesList.add(windowStart.plusDays(i - daysBeforeSunday.toLong()))
    }
    return datesList
}

fun startOfWeekSunday(date: LocalDate): LocalDate {
    val daysFromSunday = date.dayOfWeek.value % 7
    return date.minusDays(daysFromSunday.toLong())
}

fun max(a: LocalDate, b: LocalDate): LocalDate = if (a.isBefore(b)) b else a
