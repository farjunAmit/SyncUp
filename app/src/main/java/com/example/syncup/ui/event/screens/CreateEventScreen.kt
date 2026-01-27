package com.example.syncup.ui.event.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.DecisionMode
import com.example.syncup.data.model.events.PartOfDay
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.ui.event.components.CreateEventCalendar
import com.example.syncup.ui.event.components.TimeSlotChooseSheet
import com.example.syncup.ui.event.vm.CreateEventViewModel
import java.time.LocalDate
import androidx.compose.runtime.collectAsState
import com.example.syncup.ui.event.components.CreateEventTypeDialog
import com.example.syncup.ui.event.components.EventTypesDropDown

/**
 * Screen for creating a new event inside a specific group.
 *
 * The user provides:
 * - title
 * - description
 * - a set of possible [TimeSlot]s (date + part of day) that participants will vote on
 *
 * UI flow:
 * 1) The calendar shows a time window (currently: two weeks).
 * 2) Clicking a calendar cell sets [selectedDate].
 * 3) When [selectedDate] is not null, a bottom sheet ([TimeSlotChooseSheet]) is shown
 *    to choose Morning/Evening for that date.
 * 4) On confirmation, the selected date + part of day are converted into a [TimeSlot]
 *    and added to [possibleSlots].
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    viewModel: CreateEventViewModel,
    groupId: Long,
    onBack: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    val eventTypes = state.eventTypes
    val currentEventType = state.selectedEventType

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val possibleSlots = remember { mutableStateListOf<TimeSlot>() }

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var decisionMode by remember { mutableStateOf(DecisionMode.ALL_OR_NOTHING) }
    var showCreateNewTypeDialog by remember { mutableStateOf(false) }

    LaunchedEffect(groupId) {
        viewModel.loadEventTypes(groupId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Event") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Event Title") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Event Description") },
                singleLine = false,
                minLines = 3
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                EventTypesDropDown(
                    eventTypes = eventTypes,
                    currentEventType = currentEventType,
                    onItemClick = {eventType ->
                        viewModel.setEventType(eventType.id)
                    })
                IconButton(
                    onClick = { showCreateNewTypeDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Event Type"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            CreateEventCalendar(
                onCellClick = { date ->
                    selectedDate = date
                },
                isSelected = { date ->
                    possibleSlots.any { it.date == date }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = decisionMode == DecisionMode.ALL_OR_NOTHING,
                    onClick = { decisionMode = DecisionMode.ALL_OR_NOTHING }
                )
                Text("All or nothing")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = decisionMode == DecisionMode.POINTS,
                    onClick = { decisionMode = DecisionMode.POINTS }
                )
                Text("Points")
            }
            Button(
                onClick = {
                    val sortedSlots = possibleSlots.sortedBy { it.date }.toMutableSet()

                    viewModel.createEvent(
                        groupId,
                        title,
                        sortedSlots,
                        description,
                        decisionMode,
                        currentEventType?.id
                    )
                    onBack()
                },
                enabled = title.isNotBlank() && possibleSlots.isNotEmpty()
            ) { Text("Create Event") }
        }
    }

    selectedDate?.let { date ->
        TimeSlotChooseSheet(
            date = date,
            initialMorning = possibleSlots.any { timeSlot ->
                timeSlot.date == date &&
                        (timeSlot.partOfDay == PartOfDay.MORNING || timeSlot.partOfDay == PartOfDay.ALL_DAY)
            },
            initialEvening = possibleSlots.any { timeSlot ->
                timeSlot.date == date &&
                        (timeSlot.partOfDay == PartOfDay.EVENING || timeSlot.partOfDay == PartOfDay.ALL_DAY)
            },
            onDismiss = { selectedDate = null },
            onTimeSlotSelected = { morning, evening, _ ->
                val partOfDay: PartOfDay = when {
                    morning && evening -> PartOfDay.ALL_DAY
                    morning -> PartOfDay.MORNING
                    else -> PartOfDay.EVENING
                }

                possibleSlots.removeIf { it.date == date }

                if (morning || evening) {
                    possibleSlots.add(TimeSlot(date, partOfDay))
                }
                selectedDate = null
            }
        )
    }

    if (showCreateNewTypeDialog) {
        CreateEventTypeDialog(
            onDismissRequest = { showCreateNewTypeDialog = false },
            onConfirm = { type, color ->
                viewModel.addEventType(groupId, type, color)
                showCreateNewTypeDialog = false
            }
        )
    }
}
