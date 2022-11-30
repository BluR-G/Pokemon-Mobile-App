package com.example.pokemon.objects

<<<<<<< HEAD
import android.widget.Toast
import java.io.Serializable

class PokemonCollection : Serializable{
    private lateinit var pokemons: ArrayList<Pokemon>
=======
import java.io.Serializable

class PokemonCollection : Serializable {
    private var pokemons: ArrayList<Pokemon> = ArrayList()
>>>>>>> cd0a12c3e24620c153146ac8800d7b26dc9e0021

    fun addPokemonToCollection(pokemon: Pokemon){
            this.pokemons.add(pokemon)
    }

    fun removePokemonFromCollection(pokemon: Pokemon){
        this.pokemons.remove(pokemon)
    }


    fun switchPokemonToCollection(pokemon: Pokemon, pokemonTeam: PokemonTeam) {
        pokemonTeam.removePokemonFromTeam(pokemon)
        addPokemonToCollection(pokemon)
    }

    fun switchPokemonTeamToCollection(pokemonInTeam: Pokemon, pokemonTeam: PokemonTeam, pokemonInCollection: Pokemon){
        pokemonTeam.removePokemonFromTeam(pokemonInTeam)
        addPokemonToCollection(pokemonInTeam)
        pokemonTeam.addPokemonToTeam(pokemonInCollection)
    }

    fun getSize(): Int {
        return this.pokemons.size
    }

    fun getPokemon(index: Int): Pokemon {
        if (index < pokemons.size){
            return pokemons[index]
        }
        // if index incorrect, returns the last pokemon
        return pokemons[pokemons.size - 1]

    }
}