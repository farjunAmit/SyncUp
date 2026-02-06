package com.example.syncup.ui.group.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.events.EventStatus
import com.example.syncup.ui.group.components.GroupCalendar
import com.example.syncup.ui.group.vm.GroupDetailViewModel

/**
 * GroupDetailScreen
 *
 * Screen responsible for displaying details of a specific group.
 * This screen is reached via navigation and receives the groupId
 * as a navigation argument.
 *
 * Planned responsibilities:
 * - Display group information
 * - Show and manage the group's shared calendar
 * - Provide navigation back to the previous screen
 *
 * Note: This screen is currently a placeholder and will be
 * implemented incrementally.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDetailScreen(
    viewModel: GroupDetailViewModel,
    groupId: Long?,
    onGroupSelected: (Long) -> Unit,
    onCreateEvent: () -> Unit,
    onEventSelected: (Long) -> Unit,
    onBack: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    val groups by viewModel.groups.collectAsState()

    val index = groups.indexOfFirst { it.id == groupId }
    //If groupId is not found, default to index 0
    val selectedTabIndex = index
    // Runs loadGroup only when groupId changes, not on every recomposition
    LaunchedEffect(groupId, groups) {
        if (groupId != null && groups.isNotEmpty()) {
            viewModel.loadGroup(groupId)
        }
    }

    LaunchedEffect(groupId) {
        if (groupId != null) {
            viewModel.loadEvents(groupId)
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${state.group?.name}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onCreateEvent() }) {
                Icon(imageVector = Icons.Default.EditCalendar, contentDescription = "Create Event")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (index != -1) {
                ScrollableTabRow(selectedTabIndex = selectedTabIndex) {
                    groups.forEachIndexed { index, group ->
                        Tab(
                            selected = index == selectedTabIndex,
                            text = { Text(group.name) },
                            onClick = { onGroupSelected(group.id) }
                        )
                    }
                }
                Surface(modifier = Modifier.padding(16.dp)) {
                    GroupCalendar(events = state.scheduledEvents, eventTypes = state.eventTypes)
                }
                val activeEvents = state.events.filter {
                    it.eventStatus == EventStatus.VOTING || it.eventStatus == EventStatus.UNRESOLVED
                }
                val closedEvents = state.events
                    .filter { it.eventStatus == EventStatus.DECIDED || it.eventStatus == EventStatus.CANCELLED }
                    .sortedBy { if (it.eventStatus == EventStatus.CANCELLED) 1 else 0 }
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Text("Active:")
                    }
                    items(activeEvents) { event ->
                        Text(
                            text = "${event.title} (${event.eventStatus})",
                            modifier = Modifier.clickable { onEventSelected(event.id) }
                        )
                    }
                    item {
                        Text("Closed:")
                    }
                    items(closedEvents) { event ->
                        Text(
                            text = "${event.title} (${event.eventStatus})",
                            modifier = Modifier.clickable { onEventSelected(event.id) }
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Loading...")
                }
            }
        }
    }
}
