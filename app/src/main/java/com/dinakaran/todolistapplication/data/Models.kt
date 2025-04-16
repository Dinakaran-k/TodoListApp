package com.dinakaran.todolistapplication.data

/**
 * Represents a data class for a to-do list.
 */

data class TodoList(
    val id: Long = 0,
    val name: String,
    val itemCount: Int = 0,
    val completedCount: Int = 0,
    val nearestDueDate: String? = null
)


/**
 * Represents a data class for a to-do item.
 */

data class TodoItem(
    val id: Long = 0,
    val listId: Long,
    val name: String,
    val dueDate: String? = null,
    val isCompleted: Boolean = false
)
