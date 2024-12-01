package com.example.myapplication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.*
import com.example.myapplication.utils.FileManager

@Composable
fun MainApp(fileManager: FileManager, onDarkModeToggle: (Boolean) -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavigationGraph(navController,
            fileManager,
            Modifier.fillMaxSize().padding(paddingValues),
            onDarkModeToggle
            )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Jouer") },
            label = { Text("Jouer") },
            selected = false,
            onClick = { navController.navigate("play") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Star, contentDescription = "Classement") },
            label = { Text("Classement") },
            selected = false,
            onClick = { navController.navigate("leaderboard") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Paramètres") },
            label = { Text("Paramètres") },
            selected = false,
            onClick = { navController.navigate("settings") }
        )
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, fileManager: FileManager, modifier: Modifier = Modifier, onDarkModeToggle: (Boolean) -> Unit) {
    NavHost(navController, startDestination = "play") {
        composable("play") { PlayScreen(navController) }
        composable("leaderboard") {
            LeaderboardScreen(fileManager, currentUserName = "") // Aucun utilisateur actuel
        }
        composable("leaderboard/{currentUserName}") { backStackEntry ->
            val currentUserName = backStackEntry.arguments?.getString("currentUserName") ?: ""
            LeaderboardScreen(fileManager, currentUserName)
        }
        composable("settings") {
            SettingsScreen(
                onDarkModeToggle,
                onLandscapeToggle = { isLandscape -> /* Gérer le mode paysage */ }
            )
        }
        composable("result/{score}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            ResultScreen(navController, score, fileManager)
        }
    }
}
