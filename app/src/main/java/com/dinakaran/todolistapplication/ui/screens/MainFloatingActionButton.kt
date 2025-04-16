package com.dinakaran.todolistapplication.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.dinakaran.todolistapplication.data.TodoList

@Composable
fun MainFloatingActionButton(
    selectedList: TodoList?,
    onAddList: () -> Unit,
    onAddItem: () -> Unit
) {
    FloatingActionButton(
        onClick = {
            if (selectedList == null) onAddList() else onAddItem()
        }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add"
        )
    }
}
