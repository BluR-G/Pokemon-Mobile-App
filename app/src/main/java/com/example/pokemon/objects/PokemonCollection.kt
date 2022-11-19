package com.example.pokemon.objects

import android.widget.Toast
import java.io.Serializable

class PokemonCollection : Serializable{
    private lateinit var pokemons: ArrayList<Pokemon>

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
}