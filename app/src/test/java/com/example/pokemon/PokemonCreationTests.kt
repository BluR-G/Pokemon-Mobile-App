package com.example.pokemon

import com.example.pokemon.data.PokemonCreation
import com.example.pokemon.objects.Move
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class PokemonCreationTests {

    @Test
    fun testPokemonCreation() = runBlocking<Unit> {

        val actual = PokemonCreation().createPokemon("Charmander", "flame", 0)
        val types = ArrayList<String>()
        types.add("fire")

        val moves = ArrayList<MoveData>()
        moves.add(MoveData("scratch", 1, Move(100, 40, "PHYSICAL", 0, "OPPONENT","normal")))
        moves.add(MoveData("leer", 15, Move(100, 0, "SPECIAL", 0, "OPPONENT","normal")))
        moves.add(MoveData("growl", 1, Move(100, 0, "SPECIAL", 0, "OPPONENT","normal")))
        moves.add(MoveData("ember", 9, Move(100, 40, "SPECIAL", 0, "OPPONENT","fire")))
        moves.add(MoveData("flamethrower", 38, Move(100, 90, "SPECIAL", 0, "OPPONENT","fire")))
        moves.add(MoveData("fire-spin", 46, Move(85, 35, "SPECIAL", 0, "OPPONENT", "fire")))
        moves.add(MoveData("rage", 22, Move(100, 20, "PHYSICAL", 0, "OPPONENT", "normal")))
        moves.add(MoveData("slash", 30, Move(100, 70, "PHYSICAL", 0, "OPPONENT", "normal")))

        val images = ArrayList<String>()
        images.add("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png")
        images.add("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/4.png")

        val expected = Pokemon(4, "charmander", "flame", 0, 62, types, 39, 52, 43, 60, 50, 65, moves, images)
        Assert.assertEquals(actual.getId(), expected.getId())
        Assert.assertEquals(actual.getSpecies(), expected.getSpecies())
        Assert.assertEquals(actual.getName(), expected.getName())
        Assert.assertEquals(actual.getLevel(), expected.getLevel())
        Assert.assertEquals(actual.getExperienceReward(), expected.getExperienceReward())
        Assert.assertEquals(actual.getTypes()[0], expected.getTypes()[0])
        Assert.assertEquals(actual.getMaxHp(), expected.getMaxHp())
        Assert.assertEquals(actual.getAttack(), expected.getAttack())
        Assert.assertEquals(actual.getDefense(), expected.getDefense())
        Assert.assertEquals(actual.getSpecialAttack(), expected.getSpecialAttack())
        Assert.assertEquals(actual.getSpecialDefense(), expected.getSpecialDefense())
        Assert.assertEquals(actual.getSpeed(), expected.getSpeed())
        Assert.assertEquals(actual.getImages()[0], expected.getImages()[0])
        Assert.assertEquals(actual.getImages()[1], expected.getImages()[1])
    }
}