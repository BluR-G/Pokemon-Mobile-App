package com.example.pokemon.objects

import java.io.Serializable

data class MoveData (
    val moveName: String,
    val level_learned_at: Int,
    val move: Move,
    ) : Serializable
