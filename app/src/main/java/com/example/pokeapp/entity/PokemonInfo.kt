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
)
