package com.dinakaran.todolistapplication.data

import android.content.ContentValues
import com.dinakaran.todolistapplication.data.TodoDatabaseHelper.Companion.COLUMN_NAME
import com.dinakaran.todolistapplication.data.TodoDatabaseHelper.Companion.TABLE_LIST

/**
 * Represents a repository for managing to-do  list and items.
 */
class TodoRepository(private val dbHelper: TodoDatabaseHelper) {


    fun addTodoList(name: String): Long {

        //add the list to the database
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
        }
        val list = db.insert(TABLE_LIST, null, values)
        return list
    }

    fun updateTodoList(id: Long, name: String): Boolean {

        //check if the name is already exists
        if (isListNameExists(name, id))  return false

        //if not, update the list
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
        }
        val list = db.update(TABLE_LIST,values,
            "${TodoDatabaseHelper.COLUMN_ID} =?",
            arrayOf(id.toString()))

        return list > 0
    }

    fun deleteTodoList(listId: Long) {
        //delete the list from the database
        val db = dbHelper.writableDatabase

        // for items
        db.delete(TodoDatabaseHelper.TABLE_ITEM,
            "${TodoDatabaseHelper.COLUMN_LIST_ID} =?",
            arrayOf(listId.toString()))

        // for lists
        db.delete(TABLE_LIST, "${TodoDatabaseHelper.COLUMN_ID} =?", arrayOf(listId.toString()))
    }

    fun isListNameExists(name: String, excludeListId: Long=-1): Boolean {

        //check if the name is already exists in the database
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            TABLE_LIST,
            arrayOf(COLUMN_NAME),
            "LOWER($COLUMN_NAME) = LOWER(?) AND ${TodoDatabaseHelper.COLUMN_ID} != ?",
            arrayOf(name, excludeListId.toString()),
            null,
            null,
            null )

        val isNameTaken = cursor.use { it.count > 0 }
        return isNameTaken
    }



    fun updateItem(item : TodoItem) : Boolean{

        //check if the name is already exists
        if (isItemNameExists(item.listId, item.name, item.id)) return false

        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, item.name)
            put(TodoDatabaseHelper.COLUMN_DUE_DATE, item.dueDate)
            put(TodoDatabaseHelper.COLUMN_IS_COMPLETED, if (item.isCompleted) 1 else 0)
            put(TodoDatabaseHelper.COLUMN_LIST_ID,item.listId)
        }

        //update the item in the database
        val updatedItem = db.update(
            TodoDatabaseHelper.TABLE_ITEM,
            values, "${TodoDatabaseHelper.COLUMN_ID} =?",
            arrayOf(item.id.toString())
        ) > 0

        return updatedItem
    }

    fun getAllTodoList(): List<TodoList> {

        // Get all to do list from database
        val list = mutableListOf<TodoList>()
        val db = dbHelper.readableDatabase

        val query = "SELECT list.${TodoDatabaseHelper.COLUMN_ID}, " +
                "list.$COLUMN_NAME, " +
                "COUNT(item.${TodoDatabaseHelper.COLUMN_ID}) AS itemCount, " +
                "SUM(CASE WHEN item.${TodoDatabaseHelper.COLUMN_IS_COMPLETED} = 1 " +
                "THEN  1 ELSE 0 END) AS completedCount, " +
                "MIN(item.${TodoDatabaseHelper.COLUMN_DUE_DATE}) AS nearestDueDate " +
                "FROM $TABLE_LIST list " +
                "LEFT JOIN ${TodoDatabaseHelper.TABLE_ITEM} item ON list.${TodoDatabaseHelper.COLUMN_ID} = item.${TodoDatabaseHelper.COLUMN_LIST_ID} " +
                "GROUP BY list.${TodoDatabaseHelper.COLUMN_ID} "

        val cursor = db.rawQuery(query, null)

        // Iterate through the cursor and add each to-do list to the list
        cursor.use {
            while (it.moveToNext()) {
                list.add(
                    TodoList(
                        id = it.getLong(it.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_ID)),
                        name = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME)),
                        itemCount = it.getInt(it.getColumnIndexOrThrow("itemCount")),
                        completedCount = it.getInt(it.getColumnIndexOrThrow("completedCount")),
                        nearestDueDate = it.getString(it.getColumnIndexOrThrow("nearestDueDate"))
                    )
                )
            }
        }
        return list
    }

    fun isItemNameExists(listId: Long, itemName: String, excludeItemId: Long = -1): Boolean {

        // Checks if the item name is already exists in the database.
        val db = dbHelper.readableDatabase
        val query = " LOWER ($COLUMN_NAME) = LOWER(?) " +
                "AND ${TodoDatabaseHelper.COLUMN_LIST_ID} = ? " +
                "AND ${TodoDatabaseHelper.COLUMN_ID} != ? "

        val cursor = db.query(
            TodoDatabaseHelper.TABLE_ITEM,
            arrayOf(TodoDatabaseHelper.COLUMN_ID),
            query, arrayOf(itemName,listId.toString(), excludeItemId.toString()),null,null,null)

        val isNameTaken = cursor.use { it.count > 0 }
        return isNameTaken
    }

    fun addItem(item: TodoItem): Long {

        // Checks if the item name is already exists in the database.
        if (isItemNameExists(item.listId, item.name)) return -1L

        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TodoDatabaseHelper.COLUMN_LIST_ID, item.listId)
            put(COLUMN_NAME, item.name)
            put(TodoDatabaseHelper.COLUMN_DUE_DATE, item.dueDate)
            put(TodoDatabaseHelper.COLUMN_IS_COMPLETED, if (item.isCompleted) 1 else 0)
        }

        val isItemAdded = db.insert(TodoDatabaseHelper.TABLE_ITEM, null, values)
        return isItemAdded
    }

    fun deleteItem(itemId: Long) {
        // Deletes the item from the database.
        val db = dbHelper.writableDatabase
        db.delete(TodoDatabaseHelper.TABLE_ITEM,
            "${TodoDatabaseHelper.COLUMN_ID} =?",
            arrayOf(itemId.toString()))
    }



    fun getItemsForList(listId: Long): List<TodoItem> {
        // Retrieves all items for a specific list from the database.
        val items = mutableListOf<TodoItem>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            TodoDatabaseHelper.TABLE_ITEM,
            null,
            "${TodoDatabaseHelper.COLUMN_LIST_ID} =?",
            arrayOf(listId.toString()),
            null,
            null,TodoDatabaseHelper.COLUMN_DUE_DATE
        )

        cursor.use {
            while (it.moveToNext()) {
                items.add(
                    TodoItem(
                        id = it.getLong(it.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_ID)),
                        listId = it.getLong(it.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_LIST_ID)),
                        name = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME)),
                        dueDate = it.getString(it.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_DUE_DATE)),
                        isCompleted = it.getInt(it.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_IS_COMPLETED)) == 1
                    )
                )
            }
        }
        return items
    }
}