package com.example.pokeapp.entity

import android.net.Uri

data class PokemonInfo(
    val id: Long,
    val name: String,
    val imageUrl: Uri,
    val types: List<String>,
    val height: String,
    val weight: String,
    val statInfo: PokemonStatInfo,
) {
    companion object {
        fun dummy() = PokemonInfo(
            id = 1L,
            name = "bulbasaur",
            imageUrl = Uri.parse("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"),
            types = listOf("grass", "poison"),
            height = "7",
            weight = "69",
            statInfo = PokemonStatInfo.dummy()
        )
    }
}
