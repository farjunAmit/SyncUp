package com.example.syncup.ui.event.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.syncup.data.model.TypeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventTypeDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (String, Long) -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { onDismissRequest }
    ) {
        var color by remember { mutableStateOf(TypeColors.colors[0]) }
        var type by remember { mutableStateOf("") }
        Column (
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextField(
                value = type,
                onValueChange = { type = it },
                label = { Text("Event Type") },
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(TypeColors.colors) { it ->
                    val isSelected = it == color
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable(onClick = { color = it })
                            .border(
                                if (isSelected) 2.dp else 0.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                            .padding(if (isSelected) 2.dp else 0.dp)
                            .background(
                                color = it,
                                shape = CircleShape
                            )
                    )
                }
            }
            Row (){
                Button(
                    onClick = { onConfirm(type, toArgbLong(color)) }
                ) {
                    Text("Create")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onDismissRequest() }
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}

private fun toArgbLong(color : Color): Long {
    return color.toArgb().toLong() and 0xFFFFFFFFL
}