package com.example.pokeapp.infrastructure.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokeapp.entity.PokemonDetailInfo

@Database(
    entities = [PokemonDetailInfo::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonInfoDao(): PokemonInfoDao
}