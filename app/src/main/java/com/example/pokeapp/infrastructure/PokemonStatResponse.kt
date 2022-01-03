package com.example.pokeapp.infrastructure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonStatResponse(
    @SerialName("base_stat") val baseStat: Long,
    @SerialName("stat") val stat: PokemonStatDetailResponse
)
