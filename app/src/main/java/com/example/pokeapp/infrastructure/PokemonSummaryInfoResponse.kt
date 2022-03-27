package com.example.pokeapp.infrastructure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSummaryInfoResponse(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
) {
    val id: Long
        // extract pokemon id from "https://pokeapi.co/api/v2/pokemon/{id}/"
        get() = Regex(""".*pokemon/(\d+)/""")
            .find(url)!!
            .groupValues[1]
            .toLong()
}