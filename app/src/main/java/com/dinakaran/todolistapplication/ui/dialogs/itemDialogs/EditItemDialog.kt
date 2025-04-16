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
import androidx.compose.ui.unit.dp
import com.dinakaran.todolistapplication.data.TodoItem
import com.dinakaran.todolistapplication.data.TodoRepository
import com.dinakaran.todolistapplication.ui.dialogs.BaseDialog
import com.dinakaran.todolistapplication.ui.dialogs.DatePickerDialog

/**
 * Represents a dialog for editing an item.
 */

@Composable
fun EditItemDialog(
    item: TodoItem,
    repository: TodoRepository,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
){
    var itemName by remember { mutableStateOf(item.name) }
    var itemDueDate by remember { mutableStateOf(item.dueDate) }
    var showDatePicker by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("")}


    BaseDialog(
        onDismissRequest = onDismissRequest,
        title = "Edit Todo Item",
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

                if(errorMessage.isNotEmpty()) {
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
                    Text(itemDueDate ?: "Set Due Date(Optional)")
                }
            }
        },
        onConfirm = {
            when {
                itemName.isBlank() -> {
                    errorMessage = "Please enter a valid name for the item."
                    return@BaseDialog
                }

                repository.isItemNameExists(item.listId,itemName,item.id) -> {
                    errorMessage = "Item name already taken."
                    return@BaseDialog
                }
                else -> {

                    val updatedItem = item.copy(
                        name = itemName,
                        dueDate = itemDueDate
                    )

                    if (repository.updateItem(updatedItem)) {
                        onConfirm()
                    } else {
                        errorMessage = "Failed to update item."
                        return@BaseDialog
                    }
                }
            }
        },
        confirmText = "Save",
        dismissText = "Cancel"

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






