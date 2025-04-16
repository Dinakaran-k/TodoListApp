package com.dinakaran.todolistapplication.ui.dialogs.itemDialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dinakaran.todolistapplication.data.TodoItem
import com.dinakaran.todolistapplication.data.TodoList
import com.dinakaran.todolistapplication.data.TodoRepository
import com.dinakaran.todolistapplication.ui.dialogs.BaseDialog

/**
 * Represents a dialog for moving an item to another list.
 */

@Composable
fun MoveItemDialog(
    item: TodoItem,
    repository: TodoRepository,
    onDismissRequest: () -> Unit,
    onMoved: () -> Unit
){
    var lists by remember { mutableStateOf(emptyList<TodoList>()) }
    var selectedListId  by remember { mutableStateOf<Long?>(null)}

    LaunchedEffect(Unit) {
        lists=repository.getAllTodoList().filter { it.id != item.listId }
    }

    BaseDialog(
        onDismissRequest = onDismissRequest,
        title = "Move Item to Another List",
        text = {
            if (lists.isEmpty()) {
                Text(
                    text = "No other lists available. Create a new list first.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                LazyColumn {
                    items(lists) { list ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedListId = list.id }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(list.name)
                            if (selectedListId == list.id) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected"
                                )
                            }
                        }
                    }
                }
            }
        },
        onConfirm = {
            selectedListId?.let { newListId ->
                val updatedItem = item.copy(listId = newListId)
                repository.updateItem(updatedItem)
                onMoved()
            }
        },
        confirmText = "Move",
        confirmEnabled = lists.isNotEmpty() && selectedListId != null,
        dismissText = "Cancel"

    )
}