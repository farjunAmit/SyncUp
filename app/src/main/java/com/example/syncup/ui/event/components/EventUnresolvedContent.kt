package com.example.syncup.ui.event.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.Event

@Composable
fun EventUnresolvedContent(
    event: Event,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(text = event.title)
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Text(text = "No suitable time slot was found based on the votes.")
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Button(
            onClick = { /* TODO: suggest new dates */ },
            enabled = false // change to true when feature is ready
        ) {
            Text("Suggest new dates")
        }
    }
}
