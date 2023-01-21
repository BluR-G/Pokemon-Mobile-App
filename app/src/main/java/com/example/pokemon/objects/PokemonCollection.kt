package com.example.pokemon.objects

import java.io.Serializable

class PokemonCollection : Serializable {
    private var pokemons: ArrayList<Pokemon> = ArrayList()

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