package com.example.pokeapp.infrastructure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpriteOfficialArtworkResponse(
    @SerialName("front_default") val imageUrl: String
)
