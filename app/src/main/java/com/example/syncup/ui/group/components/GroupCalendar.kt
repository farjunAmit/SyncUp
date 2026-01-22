package com.example.syncup.ui.group.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventType
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun GroupCalendar(
    events: Map<LocalDate, List<Event>>,
    eventTypes: Map<String, EventType>
) {
    val date: LocalDate = LocalDate.now()
    var month by remember { mutableStateOf(YearMonth.now()) }
    val days = listOf<String>("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                onClick = { month = month.minusMonths(1) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowLeft,
                    contentDescription = "Back"
                )
            }
            Text(text = month.toString(), modifier = Modifier.weight(1f), fontSize = 20.sp)
            IconButton(
                onClick = { month = month.plusMonths(1) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                    contentDescription = "Next"
                )

            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            days.forEach { day ->
                Text(text = day, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(items = createDaysForMonth(month)) { day ->
                if (day != null) {
                    GroupCalendarDey(
                        day = day,
                        events = events[day],
                        isPass = day.isBefore(date),
                        onDayClick = { /*TODO*/ },
                        eventTypes = eventTypes
                    )
                }
            }
        }
    }
}

private fun createDaysForMonth(month: YearMonth): List<LocalDate?> {
    val result = mutableListOf<LocalDate?>()

    val firstDay = month.atDay(1)
    val offset = firstDay.dayOfWeek.value % 7

    for (i in 0 until offset) {
        result.add(null)
    }

    val daysInMonth = month.lengthOfMonth()
    for (day in 1..daysInMonth) {
        result.add(month.atDay(day))
    }

    return result
}