package com.example.myapplication

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.random.Random

data class PokemonUiState(
    val id: Int = 0,
    val name: String = "",
    val imageUrl: String = "",
    val isLoading: Boolean = true
)

class MainViewModel : ViewModel() {
    private val _uiState = mutableStateOf(PokemonUiState())
    val uiState: State<PokemonUiState> get() = _uiState

    fun fetchRandomPokemon() {
        viewModelScope.launch {
            _uiState.value = PokemonUiState(isLoading = true)
            try {
                val randomId = Random.nextInt(1, 1025)
                val response = RetrofitInstance.api.getPokemon(randomId)

                _uiState.value = PokemonUiState(
                    id = response.pokedex_id,
                    name = response.name.fr,
                    imageUrl = response.sprites.regular,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = PokemonUiState(isLoading = false)
            }
        }
    }
}
