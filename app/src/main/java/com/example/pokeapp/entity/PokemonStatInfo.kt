package com.example.pokeapp.entity

data class PokemonStatInfo(
    val hp: Long,
    val attack: Long,
    val defense: Long,
    val specialAttack: Long,
    val specialDefense: Long,
    val speed: Long
) {
    companion object {
        fun dummy() = PokemonStatInfo(
            hp = 45L,
            attack = 49,
            defense = 49,
            specialAttack = 65,
            specialDefense = 65,
            speed = 45
        )
    }
}
