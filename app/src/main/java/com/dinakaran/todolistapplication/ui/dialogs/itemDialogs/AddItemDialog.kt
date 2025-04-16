package com.dinakaran.todolistapplication.ui.dialogs.itemDialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dinakaran.todolistapplication.data.TodoItem
import com.dinakaran.todolistapplication.data.TodoRepository
import com.dinakaran.todolistapplication.ui.dialogs.BaseDialog
import com.dinakaran.todolistapplication.ui.dialogs.DatePickerDialog

/**
 * Represents a dialog for adding a new item.
 */

@Composable
fun AddItemDialog(
    listId: Long,
    repository: TodoRepository,
    onDismissRequest: () -> Unit,
    onItemAdded: () -> Unit
) {
    var itemName by remember { mutableStateOf("") }
    var itemDueDate by remember { mutableStateOf<String?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }


    BaseDialog(
        onDismissRequest = onDismissRequest,
        title = "Add New Item",
        text = {
            Column {
                TextField(
                    value = itemName,
                    onValueChange = {
                        itemName = it
                        errorMessage = ""
                    },
                    label = { Text("Item Name") },
                    isError = errorMessage.isNotEmpty()
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                OutlinedButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = itemDueDate ?: "Set Due Date (Optional)",
                        color = Color.Red,
                    )
                }
            }
        },
        onConfirm = {
            when {
                itemName.isBlank() -> {
                    errorMessage = "Please enter a valid name for the item."
                    return@BaseDialog
                }

                repository.isItemNameExists(listId, itemName) -> {
                    errorMessage = "Item name already taken."
                    return@BaseDialog
                }

                else -> {

                    val item = TodoItem(
                        name = itemName,
                        dueDate = itemDueDate,
                        listId = listId
                    )

                    val result = repository.addItem(item)
                    if (result != -1L) {
                        onItemAdded()
                    } else {
                        errorMessage = "Failed to add item."
                        return@BaseDialog
                    }
                }
            }
        },
        confirmText = "Add",
    )


    if (showDatePicker) {
        DatePickerDialog(
            onDateSelected = {
                itemDueDate = it
                showDatePicker = false
            },
            onDismissRequest = { showDatePicker = false }
        )
    }
}





