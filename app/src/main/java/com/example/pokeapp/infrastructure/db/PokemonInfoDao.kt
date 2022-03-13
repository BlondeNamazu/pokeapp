package com.example.pokeapp.infrastructure.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pokeapp.entity.PokemonInfo

@Dao
interface PokemonInfoDao {
    @Query("SELECT * FROM pokemoninfo")
    suspend fun getAll(): List<PokemonInfo>

    @Query("SELECT * FROM pokemoninfo WHERE id BETWEEN :min AND :max")
    suspend fun getInRange(min: Long, max: Long): List<PokemonInfo>

    @Query("SELECT * FROM pokemoninfo WHERE id = :id")
    suspend fun get(id: Long): List<PokemonInfo>

    @Insert
    suspend fun insert(pokemonInfoList: List<PokemonInfo>)
}