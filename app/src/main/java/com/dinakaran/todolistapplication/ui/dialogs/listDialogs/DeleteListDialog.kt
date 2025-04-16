package com.dinakaran.todolistapplication.ui.dialogs.listDialogs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.dinakaran.todolistapplication.data.TodoList
import com.dinakaran.todolistapplication.data.TodoRepository
import com.dinakaran.todolistapplication.ui.dialogs.BaseDialog

/**
 * Represents a dialog for deleting a list.
 */

@Composable
fun DeleteListDialog(
    list: TodoList,
    repository: TodoRepository,
    onDismiss: () -> Unit,
    onListDeleted: () -> Unit
) {

    BaseDialog (
        onDismissRequest = onDismiss,
        title = "Delete List",
        text = {
            Text(
                text = "Are you sure you want to delete '${list.name}'? This will delete all items in this list."
            )
        },
        onConfirm = {
            repository.deleteTodoList(list.id)
            onListDeleted()
        },
        dismissText = "Cancel"
    )
}