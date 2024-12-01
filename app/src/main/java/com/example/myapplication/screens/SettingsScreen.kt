package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.utils.Orientation
import com.example.myapplication.utils.rememberOrientation

@Composable
fun SettingsScreen(
    onDarkModeToggle: (Boolean) -> Unit,
    onLandscapeToggle: (Boolean) -> Unit
) {
    var isDarkModeEnabled by remember { mutableStateOf(false) }
    var isLandscapeEnabled by remember { mutableStateOf(false) }
    val orientation = rememberOrientation() // Détecte l'orientation

    if (orientation == Orientation.Portrait) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            ContentSettings(
                isDarkModeEnabled,
                isLandscapeEnabled,
                onDarkModeToggle = {
                    isDarkModeEnabled = it
                    onDarkModeToggle(it)
                },
                onLandscapeToggle = {
                    isLandscapeEnabled = it
                    onLandscapeToggle(it)
                }
            )
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContentSettings(
                isDarkModeEnabled,
                isLandscapeEnabled,
                onDarkModeToggle = {
                    isDarkModeEnabled = it
                    onDarkModeToggle(it)
                },
                onLandscapeToggle = {
                    isLandscapeEnabled = it
                    onLandscapeToggle(it)
                }
            )
        }
    }
}

@Composable
fun ContentSettings(
    isDarkModeEnabled: Boolean,
    isLandscapeEnabled: Boolean,
    onDarkModeToggle: (Boolean) -> Unit,
    onLandscapeToggle: (Boolean) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp), // Plus d'espace pour cliquer
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mode sombre",
                modifier = Modifier.weight(1f)
            )
            CustomSwitch(
                isChecked = isDarkModeEnabled,
                onCheckedChange = onDarkModeToggle
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp), // Plus d'espace pour cliquer
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Autoriser le mode paysage",
                modifier = Modifier.weight(1f)
            )
            CustomSwitch(
                isChecked = isLandscapeEnabled,
                onCheckedChange = onLandscapeToggle
            )
        }
    }
}

@Composable
fun CustomSwitch(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier.size(48.dp), // Taille du bouton
        contentAlignment = Alignment.Center
    ) {
        // Change l'image en fonction de l'état du switch
        val switchImage: Painter = if (isChecked) {
            painterResource(id = R.drawable.green_checked_switch)
        } else {
            painterResource(id = R.drawable.default_switch)
        }

        // Affiche l'image correspondante
        Image(
            painter = switchImage,
            contentDescription = if (isChecked) "Activé" else "Désactivé",
            modifier = Modifier.fillMaxSize()
        )

        // Zone cliquable pour activer/désactiver
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    uncheckedThumbColor = androidx.compose.ui.graphics.Color.Transparent,
                    uncheckedTrackColor = androidx.compose.ui.graphics.Color.Transparent,
                    checkedThumbColor = androidx.compose.ui.graphics.Color.Transparent,
                    checkedTrackColor = androidx.compose.ui.graphics.Color.Transparent
                )
            )
        }
    }
}
