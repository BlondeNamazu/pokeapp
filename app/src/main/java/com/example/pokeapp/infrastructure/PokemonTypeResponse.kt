package com.example.pokeapp.infrastructure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypeResponse(
    @SerialName("type") val type: PokemonTypeDetailResponse
)
