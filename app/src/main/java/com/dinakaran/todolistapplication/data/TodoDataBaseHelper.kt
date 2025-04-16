package com.dinakaran.todolistapplication.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Represents a helper class for managing the SQLite database for the to-do list app.
 */

class TodoDatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "todo.db"
        private const val DATABASE_VERSION = 1

        // Table
        const val TABLE_LIST = "todo_list"
        const val TABLE_ITEM = "todo_item"

        // Column names
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_LIST_ID = "list_id"
        const val COLUMN_DUE_DATE = "due_date"
        const val COLUMN_IS_COMPLETED = "is_completed"


        const val DROP_TODO_LIST = "DROP TABLE IF EXISTS $TABLE_LIST"
        const val DROP_TODO_ITEM = "DROP TABLE IF EXISTS $TABLE_ITEM"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        // Queries
        val createTodoList =
            "CREATE TABLE $TABLE_LIST ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT  UNIQUE NOT NULL)"

        val createTodoItem =
            "CREATE TABLE $TABLE_ITEM ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " $COLUMN_LIST_ID INTEGER NOT NULL," +
                    " $COLUMN_NAME TEXT NOT NULL," +
                    " $COLUMN_DUE_DATE TEXT," +
                    " $COLUMN_IS_COMPLETED INTEGER NOT NULL DEFAULT 0," +
                    "FOREIGN KEY($COLUMN_LIST_ID) REFERENCES $TABLE_LIST($COLUMN_ID) ON DELETE CASCADE," +
                    "CHECK($COLUMN_IS_COMPLETED IN (0, 1)))"

        // Execute the query
        db?.execSQL(createTodoList)
        db?.execSQL(createTodoItem)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //used for database upgrade
        db?.execSQL(DROP_TODO_LIST)
        db?.execSQL(DROP_TODO_ITEM)
        onCreate(db)
    }
}