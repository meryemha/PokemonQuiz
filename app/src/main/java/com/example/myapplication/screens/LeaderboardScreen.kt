package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.utils.FileManager
import com.example.myapplication.utils.Orientation
import com.example.myapplication.utils.rememberOrientation

@Composable
fun LeaderboardScreen(fileManager: FileManager, currentUserName: String) {
    val leaderboard = fileManager.getLeaderboard()
    val orientation = rememberOrientation() // Détecte l'orientation

    if (orientation == Orientation.Portrait) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            ContentLeaderboard(leaderboard, currentUserName)
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            ContentLeaderboard(leaderboard, currentUserName)
        }
    }
}

@Composable
fun ContentLeaderboard(leaderboard: List<Pair<String, Int>>, currentUserName: String) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        item {
            Text(
                text = "Classement",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        items(leaderboard) { (name, score) ->
            val isCurrentUser = name == currentUserName
            Text(
                text = if (isCurrentUser) "⭐ Joueur : $name - Score : $score ⭐" else "Joueur : $name - Score : $score",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
