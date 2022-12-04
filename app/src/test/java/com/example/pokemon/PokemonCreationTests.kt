package com.example.pokemon

import com.example.pokemon.data.PokemonCreation
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PokemonCreationTests {
    @Test
    suspend fun createPokemon_isCorrect() {
        val actual = PokemonCreation().createPokemon("Charmander", "flame", 0)
        val types = ArrayList<String>()
        types.add("fire")

//        val moves = ArrayList<MoveData>()
//        moves.add(MoveData("scratch", 1, ))
//        val expected = Pokemon(4, "charmender", "flame", 0, types, 39, 52, 43, 65, 60, 50, )
    }
}