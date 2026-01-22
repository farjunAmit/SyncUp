package com.example.syncup.ui.group.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventType
import com.example.syncup.data.model.events.PartOfDay
import com.example.syncup.ui.event.components.EventLabel
import java.time.LocalDate

@Composable
fun GroupCalendarDey(
    modifier: Modifier = Modifier,
    day: LocalDate,
    events: List<Event>?,
    eventTypes : Map<String, EventType>,
    isPass: Boolean = false,
    onDayClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(90.dp)
            .fillMaxSize()
            .border(width = 1.dp, color = Color.LightGray, shape = RectangleShape)
            .alpha(if (isPass) 0.35f else 1f)
            .clickable(enabled = !isPass) { onDayClick() }
            .padding(6.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(text = day.dayOfMonth.toString())
        if (events != null) {
            val sortedEvents = events.sortedWith { event1, event2 ->
                isBefore(event1, event2)
            }
            sortedEvents.forEach { event ->
                EventLabel(
                    event = event,
                    eventType = eventTypes[event.id]
                )
            }
        }
    }
}

private fun partOfDayOrder(part: PartOfDay): Int {
    return when (part) {
        PartOfDay.ALL_DAY -> 0
        PartOfDay.MORNING -> 1
        PartOfDay.EVENING -> 2
    }
}

private fun isBefore(event1: Event, event2: Event): Int {
    val partOfDay1 = event1.finalDate?.partOfDay
    val partOfDay2 = event2.finalDate?.partOfDay
    if (partOfDay1 == null && partOfDay2 == null) {
        return 0
    }
    if (partOfDay1 == null) {
        return 1
    }
    if (partOfDay2 == null) {
        return -1
    }
    return partOfDayOrder(partOfDay1) - partOfDayOrder(partOfDay2)
}

