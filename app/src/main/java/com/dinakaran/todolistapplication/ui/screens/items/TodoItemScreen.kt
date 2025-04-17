package com.dinakaran.todolistapplication.ui.screens.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.dinakaran.todolistapplication.data.TodoItem
import com.dinakaran.todolistapplication.data.TodoList
import com.dinakaran.todolistapplication.data.TodoRepository
import com.dinakaran.todolistapplication.ui.dialogs.itemDialogs.EditItemDialog
import com.dinakaran.todolistapplication.ui.dialogs.itemDialogs.MoveItemDialog

/**
 * Displays a list of to-do items in a lazy column
 */

@Composable
fun TodoItemScreen(
    lists: TodoList?,
    items: List<TodoItem>,
    repository: TodoRepository,
    onItemsChanged: (List<TodoItem>) -> Unit
) {
    var showMoveDialog by remember { mutableStateOf<TodoItem?>(null) }
    var showEditDialog by remember { mutableStateOf<TodoItem?>(null) }


    Column {
        Text(
            text = "Completed Items: ${items.count { it.isCompleted }}/${items.size}",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn {
            items(items) { item ->
                TodoItemRow(
                    item = item,
                    onToggleCompleted = {
                        repository.updateItem(item.copy(isCompleted = !item.isCompleted))
                        onItemsChanged(repository.getItemsForList(lists?.id ?: 0))
                    },
                    onMove = { showMoveDialog = item },
                    onEdit = { showEditDialog = item },
                    onDelete = {
                        repository.deleteItem(item.id)
                        onItemsChanged(repository.getItemsForList(lists?.id ?: 0))
                    }
                )
            }
        }

        if (showMoveDialog != null) {
            MoveItemDialog(
                item = showMoveDialog!!,
                repository = repository,
                onDismissRequest = { showMoveDialog = null },
                onMoved = {
                    onItemsChanged(repository.getItemsForList(lists?.id ?: 0))
                    showMoveDialog = null
                }
            )
        }
        if (showEditDialog!=null){
            EditItemDialog(
                item = showEditDialog!!,
                repository = repository,
                onDismissRequest = { showEditDialog = null },
                onConfirm = {
                    onItemsChanged(repository.getItemsForList(lists?.id ?: 0))
                    showEditDialog = null
                }
            )
        }
    }
}