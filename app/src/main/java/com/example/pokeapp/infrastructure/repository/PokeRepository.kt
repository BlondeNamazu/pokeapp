package com.example.pokeapp.infrastructure.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokeapp.entity.PokemonDetailInfo
import com.example.pokeapp.entity.PokemonSummaryInfo
import com.example.pokeapp.infrastructure.PokeApi
import com.example.pokeapp.infrastructure.db.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val pokeApi: PokeApi,
    private val appDatabase: AppDatabase
) {
    suspend fun fetchPokemonInfo(id: Long) {
        val dataInDb = appDatabase.pokemonInfoDao().get(id)
        if (dataInDb.isEmpty()) {
            val response = pokeApi.getPokemonInfo(id)
            if (!response.isSuccessful) throw Exception()
            appDatabase.pokemonInfoDao().insert(listOf(response.body()!!.toPokemonInfoDetail()))
        }
    }

    fun getPokemonDetailFlow(id: Long): Flow<PokemonDetailInfo> {
        return appDatabase.pokemonInfoDao().getFlow(id)
    }

    fun getPokemonListFlow(): Flow<PagingData<PokemonSummaryInfo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PokePagingSource(pokeApi) }
        ).flow
    }

    suspend fun updateFavoriteState(
        id: Long,
        isFavorite: Boolean
    ) {
        val currentInfo = appDatabase.pokemonInfoDao().get(id).firstOrNull() ?: return
        appDatabase.pokemonInfoDao().insert(
            listOf(
                currentInfo.copy(isFavorite = isFavorite)
            )
        )
    }
}