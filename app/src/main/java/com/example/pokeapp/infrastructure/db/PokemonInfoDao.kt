package com.example.pokeapp.infrastructure.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokeapp.entity.PokemonDetailInfo

@Dao
interface PokemonInfoDao {
    @Query("SELECT * FROM PokemonInfoDetail")
    suspend fun getAll(): List<PokemonDetailInfo>

    @Query("SELECT * FROM PokemonInfoDetail WHERE id IN (:ids)")
    suspend fun get(ids: List<Long>): List<PokemonDetailInfo>

    @Query("SELECT * FROM PokemonInfoDetail WHERE id = :id")
    suspend fun get(id: Long): List<PokemonDetailInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemonInfoList: List<PokemonDetailInfo>)
}