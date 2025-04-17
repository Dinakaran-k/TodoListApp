package com.dinakaran.todolistapplication.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dinakaran.todolistapplication.data.TodoList
import com.dinakaran.todolistapplication.data.TodoRepository
import com.dinakaran.todolistapplication.ui.dialogs.listDialogs.DeleteListDialog
import com.dinakaran.todolistapplication.ui.dialogs.listDialogs.EditListDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Represents a row in the list of to-do lists.
 */

@Composable
fun TodoListItem(
    list: TodoList,
    repository: TodoRepository,
    onClick: () -> Unit,
    onRefresh: () -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    val today = remember { LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) }

    val backgroundColor = when {
        list.nearestDueDate == today -> Color(0xffffff00) // yellow
        list.nearestDueDate != null && list.nearestDueDate < today -> Color(0xFFFF5252) //red
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onClick() }
            ) {
                Text(
                    text = list.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Items: ${list.completedCount}/${list.itemCount}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (list.nearestDueDate != null) {
                    Text(
                        text = "Next due: ${list.nearestDueDate}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Row {
                IconButton(onClick = { showEditDialog = true }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit List")
                }
                IconButton(onClick = { showDeleteConfirm = true }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete List")
                }
            }
        }
    }

    if (showEditDialog) {
        EditListDialog(
            list = list,
            repository = repository,
            onDismiss = { showEditDialog = false },
            onListUpdated = {
                onRefresh()
                showEditDialog = false
            }
        )
    }

    if (showDeleteConfirm) {
        DeleteListDialog(
            list = list,
            repository = repository,
            onDismiss = { showDeleteConfirm = false },
            onListDeleted = {
                onRefresh()
                showDeleteConfirm = false
            }
        )
    }
}