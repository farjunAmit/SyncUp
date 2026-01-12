package com.example.syncup.ui.group.components.GroupItem
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.syncup.data.model.Group

@Composable
fun GroupItem(group: Group, onGroupClick: () -> Unit, onDelete: () -> Unit){
    Row{
        Row (modifier = Modifier
                        .weight(1f)
                        .clickable { onGroupClick() })
        {
            Text(text = group.name)
        }
        Button(onClick = onDelete) {
            Text("Delete")
        }
    }
}