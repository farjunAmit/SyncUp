package com.example.syncup.ui.event.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.Event
import com.example.syncup.data.model.events.EventStatus

@Composable
fun EventDetailsContent(
    event: Event,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(text = event.title)
        Spacer(modifier = Modifier.padding(top = 6.dp))
        Text(
            text = when (event.eventStatus) {
                EventStatus.DECIDED -> "Status: Decided"
                EventStatus.CANCELLED -> "Status: Cancelled"
                else -> ""
            }
        )

        event.finalDate?.let { final ->
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(text = "Final date:")
            Text(text = final.toString())
        }

        if (event.description.isNotBlank()) {
            Spacer(modifier = Modifier.padding(top = 12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.padding(top = 12.dp))
            Text(text = event.description)
        }
    }
}
