package com.example.pokeapp.entity

data class PokemonSummaryInfo(
    val id: Long,
    val name: String
) {
    companion object {
        fun dummy() = PokemonSummaryInfo(
            id = 1L,
            name = "bulbasaur"
        )
    }
}
