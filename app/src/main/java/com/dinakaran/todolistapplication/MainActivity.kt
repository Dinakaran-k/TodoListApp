package com.dinakaran.todolistapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dinakaran.todolistapplication.data.TodoDatabaseHelper
import com.dinakaran.todolistapplication.data.TodoRepository
import com.dinakaran.todolistapplication.ui.screens.list.MainScreen
import com.dinakaran.todolistapplication.ui.screens.utils.SplashScreen
import com.dinakaran.todolistapplication.ui.theme.TodoListAppTheme
import kotlinx.coroutines.delay

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
                var showSplash by remember { mutableStateOf(true) }

                // Launch splash delay
                LaunchedEffect(Unit) {
                    delay(3000) // splash duration (3 seconds)
                    showSplash = false
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (showSplash) {
                        SplashScreen()
                    } else {
                        MainScreen(todoRepository)
                    }
                }
            }
        }
    }
}


