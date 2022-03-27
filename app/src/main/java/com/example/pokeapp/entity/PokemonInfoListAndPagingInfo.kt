package com.example.pokeapp.entity

data class PokemonInfoListAndPagingInfo(
    val pokemonInfoList: List<PokemonInfo>,
    val pagingInfo: PagingInfo?
)
