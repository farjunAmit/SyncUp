package com.example.syncup.ui.group.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    groupId: String?,
    onGroupSelected: (String) -> Unit,
    onBack: () -> Unit
) {
    val state = viewModel.uiState.collectAsState().value
    val index = state.groups.indexOfFirst { it.id == groupId }
    //If groupId is not found, default to index 0
    val selectedTabIndex = when (index != -1) {
        true ->  index
        false -> 0
    }
    // Runs loadGroup only when groupId changes, not on every recomposition
    LaunchedEffect(groupId) {
        if (groupId != null) {
            viewModel.loadGroup(groupId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${state.group?.name}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { createNewEvent() }) {
                Icon(Icons.Filled.DateRange, contentDescription = "Add event")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ScrollableTabRow(selectedTabIndex = selectedTabIndex) {
                state.groups.forEachIndexed{index, group ->
                    Tab(
                        selected = index == selectedTabIndex,
                        text = { Text(group.name) },
                        onClick = { onGroupSelected(group.id) }
                    )
                }
            }
            Surface(modifier = Modifier.padding(16.dp)) {
                Text("Calendar (placeholder)", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

fun createNewEvent() {
    //Todo: Create new event
}
