package com.example.myapplication

import retrofit2.http.GET
import retrofit2.http.Path

data class PokemonResponse(
    val pokedex_id: Int,
    val name: Name,
    val sprites: Sprites
)

data class Name(
    val fr: String
)

data class Sprites(
    val regular: String
)

interface PokemonApi {
    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonResponse
}
