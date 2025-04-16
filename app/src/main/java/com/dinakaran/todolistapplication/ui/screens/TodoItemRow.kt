package com.dinakaran.todolistapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.dinakaran.todolistapplication.data.TodoItem

/**
 * Represents a row in the list of to-do items.
 */

@Composable
fun TodoItemRow(
    item: TodoItem,
    onToggleCompleted: () -> Unit,
    onEdit: () -> Unit,
    onMove: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isCompleted,
                onCheckedChange = { onToggleCompleted() }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onEdit() }
            ) {
                Text(text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = if (item.isCompleted) TextDecoration.LineThrough else null
                )

                if (item.dueDate != null) {
                    Text(
                        text = "Due Date: ${item.dueDate}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            IconButton(onClick = onMove) {
                Icon(
                    imageVector = Icons.Filled.SwapVert,
                    contentDescription = "Move")
            }


            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete")
            }
        }
    }
}

