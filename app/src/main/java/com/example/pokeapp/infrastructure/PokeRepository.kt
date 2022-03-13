package com.example.pokeapp.infrastructure

import com.example.pokeapp.entity.PokemonInfo
import com.example.pokeapp.infrastructure.db.AppDatabase
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val pokeApi: PokeApi,
    private val appDatabase: AppDatabase
) {
    suspend fun getPokemonList(limit: Long, offset: Long): List<PokemonInfo> {
        return (1..limit).map {
            val response = pokeApi.getPokemonInfo(offset + it)
            if (!response.isSuccessful) throw Exception()
            appDatabase.pokemonInfoDao().insert(listOf(response.body()!!.toPokemonInfo()))
            response.body()!!.toPokemonInfo()
        }
    }

    suspend fun getPokemonInfo(id: Long): PokemonInfo {
        val response = pokeApi.getPokemonInfo(id)
        if (!response.isSuccessful) throw Exception()
        return response.body()!!.toPokemonInfo()
    }
}