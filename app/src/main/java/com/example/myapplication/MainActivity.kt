package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.screens.*
import com.example.myapplication.utils.FileManager

class MainActivity : ComponentActivity() {
    private var isDarkTheme = mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fileManager = FileManager(this)

        setContent {
            MyApplicationTheme(darkTheme = isDarkTheme.value) {
                MainApp(fileManager, onDarkModeToggle = { isDarkTheme.value = it })
            }
        }
    }
}
