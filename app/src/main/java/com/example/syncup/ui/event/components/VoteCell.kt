package com.example.syncup.ui.event.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.TimeSlot

@Composable
fun VoteCell(
    timeSlot: TimeSlot,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .height(120.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = timeSlot.date.dayOfWeek.toString())
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = timeSlot.date.dayOfMonth.toString() + "/" + timeSlot.date.monthValue.toString())
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = timeSlot.partOfDay.toString())

        }
    }
}