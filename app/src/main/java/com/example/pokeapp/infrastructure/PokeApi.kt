package com.example.pokeapp.infrastructure

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon/{id}")
    suspend fun getPokemonInfo(
        @Path("id") id: Long
    ): Response<PokemonInfoResponse>

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Long,
        @Query("limit") limit: Long
    ): Response<PokemonListResponse>
}