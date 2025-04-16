package com.dinakaran.todolistapplication.ui.dialogs.listDialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.dinakaran.todolistapplication.data.TodoRepository
import com.dinakaran.todolistapplication.ui.dialogs.BaseDialog


/**
 * Represents a dialog for adding a new list.
 */

@Composable
fun AddListDialog(
    repository: TodoRepository,
    onDismissRequest: () -> Unit,
    onListAdded: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    BaseDialog(
        title = "Add New List",
        onDismissRequest = onDismissRequest,
        onConfirm = {
            if (name.isBlank()) {
                errorMessage = "Name Cannot Be blank"
                return@BaseDialog
            }

            if (repository.isListNameExists(name)){
                errorMessage = "Name Already Taken"
                return@BaseDialog
            }
            repository.addTodoList(name)
            onListAdded()
        },
        confirmText = "Add",
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("List Name") },
                    isError = errorMessage.isNotEmpty()
                )
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        },
        dismissText = "Cancel"

    )
}