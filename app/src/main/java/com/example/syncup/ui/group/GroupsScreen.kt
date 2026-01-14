package com.example.syncup.ui.group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.ui.group.components.GroupCreateSheet
import com.example.syncup.ui.group.components.GroupEmptyState
import com.example.syncup.ui.group.components.GroupGrid

/**
 * GroupsScreen
 *
 * Main screen for managing groups.
 * Responsibilities:
 * - Observe GroupsViewModel UI state (list of groups)
 * - Display either an empty state or a grid of groups
 * - Provide entry point for creating a new group (FAB + bottom sheet)
 * - Delegate user actions (open / rename / delete / create) to the ViewModel or parent
 *
 * Navigation is delegated to the parent via onGroupClick(groupId).
 * Local UI state (showCreateSheet) is kept inside the composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    viewModel: GroupsViewModel,
    onGroupClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    // Collect UI state exposed by the ViewModel (single source of truth for groups list)
    val state = viewModel.uiState.collectAsState().value

    // Local UI state controlling the visibility of the create bottom sheet
    var showCreateSheet by remember { mutableStateOf(false) }

    val isEmpty = state.groups.isEmpty()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Groups") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateSheet = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add group")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {

            Spacer(Modifier.height(12.dp))
            Text("Group: ${state.groups.size}")
            Spacer(Modifier.height(12.dp))

            if (isEmpty) {
                // Empty state encourages the user to create the first group
                GroupEmptyState(onClick = { showCreateSheet = true })
            } else {
                // Groups are presented in a grid; actions are delegated via callbacks
                GroupGrid(
                    groups = state.groups,
                    onGroupClick = { groupId -> onGroupClick(groupId) },
                    onDelete = { groupId -> viewModel.deleteGroup(groupId) },
                    onEdit = { groupId, newName -> viewModel.renameGroup(groupId, newName) },
                    modifier = modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }
        }
    }

    // Bottom sheet for creating a group (shown via FAB or empty state CTA)
    if (showCreateSheet) {
        GroupCreateSheet(
            onCreate = { name, invitedEmails ->
                viewModel.addGroup(name, invitedEmails)
                showCreateSheet = false
            },
            onCancel = { showCreateSheet = false }
        )
    }
}
