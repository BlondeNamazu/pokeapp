package com.example.pokeapp.infrastructure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypeDetailResponse(
    @SerialName("name") val name: String
)
