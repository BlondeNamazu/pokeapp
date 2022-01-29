package com.example.pokeapp.infrastructure

import com.example.pokeapp.entity.PokemonInfo
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val pokeApi: PokeApi
) {
    suspend fun getPokemonList(limit: Long, offset: Long): List<PokemonInfo> {
        return (1..limit).map {
            val response = pokeApi.getPokemonInfo(offset + it)
            if (!response.isSuccessful) throw Exception()
            response.body()!!.toPokemonInfo()
        }
    }

    suspend fun getPokemonInfo(id: Long): PokemonInfo {
        val response = pokeApi.getPokemonInfo(id)
        if (!response.isSuccessful) throw Exception()
        return response.body()!!.toPokemonInfo()
    }
}