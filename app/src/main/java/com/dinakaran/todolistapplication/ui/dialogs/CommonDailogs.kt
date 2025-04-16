package com.dinakaran.todolistapplication.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import java.time.Instant
import java.time.ZoneId.systemDefault

/**
 * Represents a base dialog for reusing common functionality.
 */

@Composable
fun BaseDialog(
    title: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    confirmText: String = "OK",
    dismissText: String = "Cancel",
    confirmEnabled: Boolean = true,
    text : @Composable () -> Unit
){
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(title) },
        text = { text() },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                enabled = confirmEnabled
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(dismissText)
            }
        }
    )
}

/**
 * Represents a dialog for Date Picker.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp),
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 3.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Select Due Date",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                DatePicker(
                    state = datePickerState,
                    showModeToggle = false,
                    title = null,
                    headline = null,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = "Cancel")
                    }
                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )


                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val localDate = Instant.ofEpochMilli(millis)
                                .atZone(systemDefault())
                                .toLocalDate()
                            onDateSelected(localDate.toString())
                        }
                        onDismissRequest()
                    }
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}