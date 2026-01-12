package com.example.syncup.ui.group.components.GroupItem

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.syncup.data.model.Group

@Composable
fun GroupGrid(groups: List<Group>,onGroupClick: (String) -> Unit, onDelete : (String) -> Unit, modifier: Modifier = Modifier){
    LazyColumn (modifier = modifier){
        items(groups){
            GroupItem(it, onGroupClick = {onGroupClick(it.id)}, onDelete = {onDelete(it.id)})
        }
    }
}