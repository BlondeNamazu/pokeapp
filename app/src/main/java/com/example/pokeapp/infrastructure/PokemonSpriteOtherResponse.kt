package com.example.pokeapp.infrastructure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpriteOtherResponse(
    @SerialName("official-artwork") val officialArtwork: PokemonSpriteOfficialArtworkResponse
)
