package com.example.myapplication.utils

import android.content.Context
import java.io.File

class FileManager(private val context: Context) {
    private val fileName = "leaderboard.txt"

    // Ajouter un utilisateur et son score dans le fichier
    fun addUserScore(userName: String, score: Int) {
        val file = File(context.filesDir, fileName)
        file.appendText("$userName:$score\n")
    }

    // Lire les données du fichier
    fun getLeaderboard(): List<Pair<String, Int>> {
        val file = File(context.filesDir, fileName)
        if (!file.exists()) {
            return emptyList()
        }

        return file.readLines()
            .mapNotNull { line ->
                val parts = line.split(":")
                if (parts.size == 2) {
                    val name = parts[0]
                    val score = parts[1].toIntOrNull()
                    if (score != null) {
                        name to score
                    } else null
                } else null
            }
            .sortedByDescending { it.second } // Trier par score décroissant
    }
}
