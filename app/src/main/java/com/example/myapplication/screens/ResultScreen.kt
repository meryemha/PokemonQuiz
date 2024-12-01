package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.utils.FileManager

@Composable
fun ResultScreen(navController: NavController, score: Int, fileManager: FileManager) {
    var playerName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Félicitations !",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text("Votre score : $score / 10", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = playerName,
            onValueChange = { playerName = it },
            label = { Text("Votre nom") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (playerName.isNotBlank()) {
                    fileManager.addUserScore(playerName, score) // Ajouter au fichier
                    navController.navigate("leaderboard/$playerName") // Naviguer vers le classement
                }
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("Enregistrer")
        }
    }
}
