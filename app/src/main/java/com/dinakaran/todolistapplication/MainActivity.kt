package com.dinakaran.todolistapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dinakaran.todolistapplication.data.TodoDatabaseHelper
import com.dinakaran.todolistapplication.data.TodoRepository
import com.dinakaran.todolistapplication.ui.screens.MainScreen
import com.dinakaran.todolistapplication.ui.theme.TodoListAppTheme

/**
 * The main activity of the application.
 */

class MainActivity : ComponentActivity() {

    private lateinit var todoRepository: TodoRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        todoRepository = TodoRepository(TodoDatabaseHelper(this))
        setContent {
            TodoListAppTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    MainScreen(todoRepository)
                }
            }
        }
    }
}

