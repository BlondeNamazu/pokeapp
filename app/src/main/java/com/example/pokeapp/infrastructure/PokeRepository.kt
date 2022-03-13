package com.example.pokeapp.infrastructure

import com.example.pokeapp.entity.PokemonInfo
import com.example.pokeapp.infrastructure.db.AppDatabase
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val pokeApi: PokeApi,
    private val appDatabase: AppDatabase
) {
    suspend fun getPokemonList(limit: Long, offset: Long): List<PokemonInfo> {
        val dataInDb = appDatabase.pokemonInfoDao().getInRange(offset + 1, offset + limit)
        if (dataInDb.size.toLong() == limit) return dataInDb
        val dataFromApi = (dataInDb.size + 1..limit).map {
            val response = pokeApi.getPokemonInfo(offset + it)
            if (!response.isSuccessful) throw Exception()
            response.body()!!.toPokemonInfo()
        }
        appDatabase.pokemonInfoDao().insert(dataFromApi)
        return dataInDb.plus(dataFromApi)
    }

    suspend fun getPokemonInfo(id: Long): PokemonInfo {
        val dataInDb = appDatabase.pokemonInfoDao().get(id)
        if (dataInDb.isNotEmpty()) return dataInDb.first()
        val response = pokeApi.getPokemonInfo(id)
        if (!response.isSuccessful) throw Exception()
        appDatabase.pokemonInfoDao().insert(listOf(response.body()!!.toPokemonInfo()))
        return response.body()!!.toPokemonInfo()
    }
}