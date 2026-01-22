package com.example.syncup.ui.event.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventType

@Composable
fun EventLabel(
    event: Event,
    eventType: EventType?
    ) {
    Card (
        modifier = Modifier.padding(3.dp),
        border = if (eventType != null) BorderStroke(1.dp, Color(eventType.color)) else null
    ) { }
}
