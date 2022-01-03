package com.example.pokeapp.infrastructure

import android.net.Uri
import com.example.pokeapp.entity.PokemonInfo
import com.example.pokeapp.entity.PokemonStatInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonInfoResponse(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("sprites") val sprites: PokemonSpriteResponse,
    @SerialName("types") val types: List<PokemonTypeResponse>,
    @SerialName("height") val height: Long,
    @SerialName("weight") val weight: Long,
    @SerialName("stats") val stats: List<PokemonStatResponse>
) {
    fun toPokemonInfo(): PokemonInfo {
        return PokemonInfo(
            id = id,
            name = name,
            imageUrl = Uri.parse(sprites.other.officialArtwork.imageUrl),
            types = types.map { it.type.name },
            height = "${height / 10}.${height % 10}",
            weight = "${weight / 10}.${weight % 10}",
            statInfo = PokemonStatInfo(
                hp = stats.first { it.stat.name == "hp" }.baseStat,
                attack = stats.first { it.stat.name == "attack" }.baseStat,
                defense = stats.first { it.stat.name == "defense" }.baseStat,
                specialAttack = stats.first { it.stat.name == "special-attack" }.baseStat,
                specialDefense = stats.first { it.stat.name == "special-defense" }.baseStat,
                speed = stats.first { it.stat.name == "speed" }.baseStat
            )
        )
    }
}