package com.example.pokeapp.infrastructure.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokeapp.entity.PokemonInfo

@Database(
    entities = [PokemonInfo::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonInfoDao(): PokemonInfoDao
}