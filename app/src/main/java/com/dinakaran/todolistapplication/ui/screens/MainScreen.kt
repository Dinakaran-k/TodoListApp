package com.dinakaran.todolistapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dinakaran.todolistapplication.data.TodoItem
import com.dinakaran.todolistapplication.data.TodoList
import com.dinakaran.todolistapplication.data.TodoRepository
import com.dinakaran.todolistapplication.ui.dialogs.itemDialogs.AddItemDialog
import com.dinakaran.todolistapplication.ui.dialogs.listDialogs.AddListDialog

/**
 * the main screen of the app
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(repository: TodoRepository) {

    var lists by remember { mutableStateOf(emptyList<TodoList>()) }
    var selectedList by remember { mutableStateOf<TodoList?>(null) }
    var showAddListScreenDialog by remember { mutableStateOf(false) }
    var showAddItemsScreenDialog by remember { mutableStateOf(false) }
    var items by remember { mutableStateOf(emptyList<TodoItem>()) }

    LaunchedEffect(Unit) {
        lists = repository.getAllTodoList()
    }

    LaunchedEffect(selectedList) {
        selectedList?.let {
            items = repository.getItemsForList(it.id)
        }
    }

    Scaffold(
        topBar = {
            MainTopAppBar(
                selectedList = selectedList,
                onBack = {
                    selectedList = null
                    lists = repository.getAllTodoList()
                }
            )
        },
        floatingActionButton = {
            MainFloatingActionButton(
                selectedList = selectedList,
                onAddList = { showAddListScreenDialog = true },
                onAddItem = { showAddItemsScreenDialog = true }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when {
                selectedList == null && lists.isEmpty() -> EmptyTodoListUI()

                selectedList == null -> TodoListScreen(
                    lists = lists,
                    repository = repository,
                    onListSelect = { list ->
                        selectedList = list
                        items = repository.getItemsForList(list.id)
                    },
                    onRefresh = {
                        lists = repository.getAllTodoList()
                    }
                )

                else -> TodoItemScreen(
                    lists = selectedList,
                    items = items,
                    repository = repository,
                    onItemsChanged = { newItems ->
                        items = newItems
                        lists = repository.getAllTodoList()
                        selectedList = lists.find { it.id == selectedList?.id }
                    }
                )
            }

            if (showAddListScreenDialog) {
                AddListDialog(
                    repository = repository,
                    onDismissRequest = { showAddListScreenDialog = false },
                    onListAdded = {
                        lists = repository.getAllTodoList()
                        showAddListScreenDialog = false
                    }
                )
            }

            if (showAddItemsScreenDialog && selectedList != null) {
                AddItemDialog(
                    listId = selectedList!!.id,
                    repository = repository,
                    onDismissRequest = { showAddItemsScreenDialog = false },
                    onItemAdded = {
                        items = repository.getItemsForList(selectedList!!.id)
                        lists = repository.getAllTodoList()
                        selectedList = lists.find { it.id == selectedList?.id }
                        showAddItemsScreenDialog = false
                    }
                )
            }
        }
    }
}








