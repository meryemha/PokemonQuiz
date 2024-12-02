package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.MainViewModel
import com.example.myapplication.utils.Orientation
import com.example.myapplication.utils.rememberOrientation
import androidx.navigation.NavController
import com.example.myapplication.PokemonUiState

@Composable
fun PlayScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState
    val orientation = rememberOrientation() // Détecter l'orientation

    // Déclarer les états nécessaires avec remember
    var userAnswer by remember { mutableStateOf("") }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }
    var currentQuestion by remember { mutableStateOf(1) }
    var score by remember { mutableStateOf(0) }

    // Charger le premier Pokémon au démarrage
    LaunchedEffect(Unit) {
        viewModel.fetchRandomPokemon()
    }

    // Différencier le mode portrait et paysage
    if (orientation == Orientation.Portrait) {
        // Mode Portrait
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ContentPlayScreen(
                uiState = uiState,
                userAnswer = userAnswer,
                isCorrect = isCorrect,
                currentQuestion = currentQuestion,
                score = score,
                onAnswerChange = { userAnswer = it },
                onValidateAnswer = { correct ->
                    isCorrect = correct
                    if (correct) score++
                    if (currentQuestion < 10) {
                        currentQuestion++
                        userAnswer = ""
                        viewModel.fetchRandomPokemon()
                    } else {
                        navController.navigate("result/$score")
                    }
                }
            )
        }
    } else {
        // Mode Paysage
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContentPlayScreen(
                uiState = uiState,
                userAnswer = userAnswer,
                isCorrect = isCorrect,
                currentQuestion = currentQuestion,
                score = score,
                onAnswerChange = { userAnswer = it },
                onValidateAnswer = { correct ->
                    isCorrect = correct
                    if (correct) score++
                    if (currentQuestion < 10) {
                        currentQuestion++
                        userAnswer = ""
                        viewModel.fetchRandomPokemon()
                    } else {
                        navController.navigate("result/$score")
                    }
                }
            )
        }
    }
}

@Composable
fun ContentPlayScreen(
    uiState: PokemonUiState,
    userAnswer: String,
    isCorrect: Boolean?,
    currentQuestion: Int,
    score: Int,
    onAnswerChange: (String) -> Unit,
    onValidateAnswer: (Boolean) -> Unit // Paramètre ajouté
) {
    if (uiState.isLoading) {
        CircularProgressIndicator()
    } else {
        Text(
            text = "Quel est ce Pokémon ?",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = rememberAsyncImagePainter(uiState.imageUrl),
            contentDescription = "Image du Pokémon",
            modifier = Modifier.size(200.dp),
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = userAnswer,
            onValueChange = onAnswerChange, // Mise à jour de la réponse
            label = { Text("Votre réponse") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .semantics {
                    contentDescription = "Insérez votre réponse"
                }
        )


        Button(
            onClick = {
                val correct = userAnswer.equals(uiState.name, ignoreCase = true)
                onValidateAnswer(correct) // Appel de la validation
            },
            modifier = Modifier.semantics {
                contentDescription = "Valider"
            }
        ) {
            Text("Valider")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Résultat de la validation
        when (isCorrect) {
            true -> Text(
                text = "Bonne réponse !",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.semantics {
                    contentDescription = "Bonne réponse"
                }
            )
            false -> Text(
                text = "Mauvaise réponse. C'était ${uiState.name}.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.semantics {
                    contentDescription = "Mauvaise réponse, la bonne réponse était ${uiState.name}"
                }
            )
            null -> {}
        }


        // Compteur de question
        Text(
            text = "Question : $currentQuestion / 10",
            modifier = Modifier
                .padding(top = 16.dp)
                .semantics {
                    // TalkBack lira ce contenu au lieu du texte brut
                    contentDescription = "Question : $currentQuestion sur dix"
                }
        )
    }
}
