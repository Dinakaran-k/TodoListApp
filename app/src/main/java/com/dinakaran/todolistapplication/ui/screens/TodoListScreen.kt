package com.dinakaran.todolistapplication.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.dinakaran.todolistapplication.data.TodoList
import com.dinakaran.todolistapplication.data.TodoRepository

/**
 * Displays a list of to-do lists items in a lazy column
 */


@Composable
fun TodoListScreen(
    lists: List<TodoList>,
    repository: TodoRepository,
    onListSelect: (TodoList) -> Unit,
    onRefresh: () -> Unit
) {
    LazyColumn {
        items(lists) { list ->
            TodoListItem(
                list = list,
                repository = repository,
                onClick = { onListSelect(list) },
                onRefresh = onRefresh
            )
        }
    }
}
