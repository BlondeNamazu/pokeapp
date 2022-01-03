package com.example.pokeapp.infrastructure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpriteResponse(
    @SerialName("other") val other: PokemonSpriteOtherResponse
)
