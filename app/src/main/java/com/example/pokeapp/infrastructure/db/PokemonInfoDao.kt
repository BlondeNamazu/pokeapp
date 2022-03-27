package com.example.pokeapp.infrastructure.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokeapp.entity.PokemonInfo

@Dao
interface PokemonInfoDao {
    @Query("SELECT * FROM pokemoninfo")
    suspend fun getAll(): List<PokemonInfo>

    @Query("SELECT * FROM pokemoninfo WHERE id IN (:ids)")
    suspend fun get(ids: List<Long>): List<PokemonInfo>

    @Query("SELECT * FROM pokemoninfo WHERE id = :id")
    suspend fun get(id: Long): List<PokemonInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemonInfoList: List<PokemonInfo>)
}