package com.example.pokeapp.infrastructure

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApi {
    @GET("pokemon/{id}")
    suspend fun getPokemonInfo(
        @Path("id") id: Long
    ): Response<PokemonInfoResponse>
}