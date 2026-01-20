package com.example.syncup.ui.event.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.TimeSlot

@Composable
fun VoteOptionsGrid(
    modifier: Modifier = Modifier,
    possibleSlots: Set<TimeSlot>,
    onSlotClick: (timeSlot: TimeSlot) -> Unit
){
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(items = possibleSlots.toList()){ slot ->
            VoteCell(
                timeSlot = slot,
                onClick = { onSlotClick(slot) }
            )
        }
    }
}