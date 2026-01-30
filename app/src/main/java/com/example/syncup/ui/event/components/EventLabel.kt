package com.example.syncup.ui.event.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventType

@Composable
fun EventLabel(
    modifier: Modifier = Modifier,
    event: Event,
    eventType: EventType?
    //onEventClick: () -> Unit
) {
    val eventColor = if (eventType != null) Color(eventType.color) else Color.Black
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(3.dp),
        colors = CardDefaults.cardColors(containerColor = eventColor.copy(alpha = 0.5f)),
        border = BorderStroke(1.dp, eventColor)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            text = event.title,
            fontSize = 11.sp,
            maxLines = 1,
        )
    }
}
