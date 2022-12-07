package com.example.pokemon

import com.example.pokemon.objects.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PokemonTeamTests {

    private lateinit var collection: PokemonCollection
    private lateinit var team: PokemonTeam

    @Before
    fun setUp() {
        collection = PokemonCollection()
        team = PokemonTeam()
    }

    @Test
    fun testAddPokemonToTeam() {
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

        val pokemon = Pokemon(4, "charmander", "flame", 0, 62,  types, 39, 52, 43, 60, 50, 65, moves, images)

        team.addPokemonToTeam(pokemon)

        Assert.assertEquals(1, team.getSize())
        Assert.assertEquals(pokemon, team.getPokemon(0))
    }

    @Test
    fun testSwitchPokemonToTeam() {
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

        val pokemon = Pokemon(4, "charmander", "flame", 0, 62,  types, 39, 52, 43, 60, 50, 65, moves, images)

        collection.addPokemonToCollection(pokemon)
        Assert.assertEquals(1, collection.getSize())

        team.switchPokemonToTeam(pokemon, collection)

        Assert.assertEquals(0, collection.getSize())
        Assert.assertEquals(1, team.getSize())
        Assert.assertEquals(pokemon, team.getPokemon(0))
    }

    @Test
    fun testRemovePokemonToTeam() {
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

        val pokemon = Pokemon(4, "charmander", "flame", 0, 62,  types, 39, 52, 43, 60, 50, 65, moves, images)

        team.addPokemonToTeam(pokemon)
        Assert.assertEquals(1, team.getSize())
        Assert.assertEquals(pokemon, team.getPokemon(0))

        team.removePokemonFromTeam(pokemon)
        Assert.assertEquals(0, team.getSize())
    }

    @Test
    fun testGetPokemon() {
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

        val pokemon = Pokemon(4, "charmander", "flame", 0, 62,  types, 39, 52, 43, 60, 50, 65, moves, images)

        team.addPokemonToTeam(pokemon)
        Assert.assertEquals(1, team.getSize())
        val pokemonFromCollection = team.getPokemon(0);

        Assert.assertEquals(4, pokemonFromCollection.getId())
        Assert.assertEquals("charmander", pokemonFromCollection.getSpecies())
        Assert.assertEquals("flame", pokemonFromCollection.getName())
        Assert.assertEquals(0, pokemonFromCollection.getLevel())
        Assert.assertEquals(62, pokemonFromCollection.getExperienceReward())
        Assert.assertEquals("fire", pokemonFromCollection.getTypes()[0])
        Assert.assertEquals(39, pokemonFromCollection.getMaxHp())
        Assert.assertEquals(52, pokemonFromCollection.getAttack())
        Assert.assertEquals(43, pokemonFromCollection.getDefense())
        Assert.assertEquals(60, pokemonFromCollection.getSpecialAttack())
        Assert.assertEquals(50, pokemonFromCollection.getSpecialDefense())
        Assert.assertEquals(65, pokemonFromCollection.getSpeed())
    }
}