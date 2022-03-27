package com.example.pokeapp.infrastructure

import com.example.pokeapp.entity.PagingInfo
import com.example.pokeapp.entity.PokemonInfo
import com.example.pokeapp.entity.PokemonInfoListAndPagingInfo
import com.example.pokeapp.infrastructure.db.AppDatabase
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val pokeApi: PokeApi,
    private val appDatabase: AppDatabase
) {
    suspend fun getPokemonList(pagingInfo: PagingInfo): PokemonInfoListAndPagingInfo {
        val listResponse = pokeApi.getPokemonList(
            offset = pagingInfo.offset,
            limit = pagingInfo.limit
        )
        if (!listResponse.isSuccessful) throw Exception()
        val newPagingInfo = listResponse.body()!!.pagingInfo
        val ids = listResponse.body()!!.summaryInfoList.map { it.id }

        val dataInDb = appDatabase.pokemonInfoDao().get(ids)
        if (dataInDb.size == ids.size) return PokemonInfoListAndPagingInfo(
            pokemonInfoList = dataInDb,
            pagingInfo = newPagingInfo
        )
        val dataFromApi = ids.minus(dataInDb.map { it.id }).map { id ->
            getPokemonInfo(id)
        }
        appDatabase.pokemonInfoDao().insert(dataFromApi)
        return PokemonInfoListAndPagingInfo(
            pokemonInfoList = dataInDb.plus(dataFromApi),
            pagingInfo = newPagingInfo
        )
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