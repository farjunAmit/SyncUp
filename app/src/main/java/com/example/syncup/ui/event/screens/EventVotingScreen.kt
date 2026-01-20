package com.example.syncup.ui.event.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.ui.event.components.VoteForSlotSheet
import com.example.syncup.ui.event.components.VoteOptionsGrid
import com.example.syncup.ui.event.vm.EventVotingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    viewModel: EventVotingViewModel,
    eventId: String,
    onBack: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    val event = state.event
    var possibleSlots: Set<TimeSlot> = emptySet()
    var chosenTimeSlot by remember { mutableStateOf<TimeSlot?>(null) }

    if (event != null) {
        possibleSlots = event.possibleSlots
    }
    LaunchedEffect(eventId) {
        viewModel.loadEvent(eventId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${event?.title}") },
                navigationIcon = {
                    IconButton(onClick = onBack)
                    {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        chosenTimeSlot?.let {
            VoteForSlotSheet(
                timeSlot = it,
                onDismiss = { chosenTimeSlot = null },
                onVote = { vote ->
                    //Todo: add vote to draft
                    chosenTimeSlot = null
                }
            )
        }
        VoteOptionsGrid(
            modifier = Modifier.padding(innerPadding),
            possibleSlots = possibleSlots,
            onSlotClick = { chosenTimeSlot = it }
        )
    }
}