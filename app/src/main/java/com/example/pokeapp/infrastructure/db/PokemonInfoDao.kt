package com.example.pokeapp.infrastructure.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokeapp.entity.PokemonDetailInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonInfoDao {
    @Query("SELECT * FROM PokemonInfoDetail WHERE id = :id")
    fun getFlow(id: Long): Flow<PokemonDetailInfo>

    @Query("SELECT * FROM PokemonInfoDetail WHERE id = :id")
    suspend fun get(id: Long): List<PokemonDetailInfo>

    @Query("SELECT * FROM PokemonInfoDetail WHERE isFavorite = 1")
    fun getFavoriteFlow(): Flow<List<PokemonDetailInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemonInfoList: List<PokemonDetailInfo>)
}