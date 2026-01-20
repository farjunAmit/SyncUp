package com.example.syncup.ui.event.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateSet
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.PartOfDay
import com.example.syncup.data.model.events.TimeSlot
import com.example.syncup.ui.event.components.CreateEventCalendar
import com.example.syncup.ui.event.components.TimeSlotChooseSheet
import com.example.syncup.ui.event.vm.CreateEventViewModel
import java.time.LocalDate

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
    groupId: String,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val possibleSlots = remember { mutableStateSetOf<TimeSlot>() }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

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
            modifier = Modifier.Companion
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Event Title") },
                singleLine = true
            )
            Spacer(modifier = Modifier.Companion.height(8.dp))
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Event Description") },
                singleLine = false,
                minLines = 3
            )
            Spacer(modifier = Modifier.Companion.height(8.dp))
            CreateEventCalendar(
                onCellClick = { date ->
                    selectedDate = date
                },
                isSelected = { date ->
                    possibleSlots.any { it.date == date }
                }
            )
            Spacer(modifier = Modifier.Companion.height(8.dp))
            Button(
                onClick = {
                    var possibleSlotsSort = possibleSlots.toList()
                    possibleSlotsSort = possibleSlotsSort.sortedBy { it.date }
                    viewModel.createEvent(groupId, title, possibleSlotsSort.toMutableSet(), description)
                    onBack()
                },
                enabled = title.isNotBlank() && possibleSlots.isNotEmpty()
            ) { Text("Create Event") }
        }
    }
    selectedDate?.let { it ->
        TimeSlotChooseSheet(
            date = it,
            initialMorning = possibleSlots.any { timeSlot ->
                timeSlot.date == it &&
                        (timeSlot.partOfDay == PartOfDay.MORNING || timeSlot.partOfDay == PartOfDay.ALL_DAY)
            },
            initialEvening = possibleSlots.any { timeSlot ->
                timeSlot.date == it &&
                        (timeSlot.partOfDay == PartOfDay.EVENING || timeSlot.partOfDay == PartOfDay.ALL_DAY)
            },
            onDismiss = { selectedDate = null },
            onTimeSlotSelected = { morning, evening, date ->
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
}
