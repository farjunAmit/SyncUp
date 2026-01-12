package com.example.syncup.ui.group

import android.R.attr.text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.syncup.ui.group.components.GroupItem.GroupGrid
import com.example.syncup.ui.group.components.GroupItem.GroupItem

@Composable
fun GroupsScreen(viewModel: GroupsViewModel, modifier: Modifier = Modifier) {
    val state = viewModel.uiState.collectAsState().value
    Column (modifier = modifier){
        Button(
            onClick = { viewModel.addGroup("new Group") }) {
            Text("Add Group")
        }
        Text("Group: ${state.groups.size}")
        GroupGrid(state.groups,{groupId -> {"here will be group click"}},{groupId -> viewModel.deleteGroup(groupId)}, modifier)
    }
}