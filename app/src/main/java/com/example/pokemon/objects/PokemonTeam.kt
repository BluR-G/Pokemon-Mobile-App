package com.example.pokemon.objects

class PokemonTeam {
    private var pokemons: ArrayList<Pokemon> = ArrayList()

    fun addPokemonToTeam(pokemon: Pokemon): Boolean{
        if(pokemons.size < 6){
            this.pokemons.add(pokemon)
            return true
        }
        return false
    }

    fun removePokemonFromTeam(pokemon: Pokemon){
        this.pokemons.remove(pokemon)
    }

    fun switchPokemonToTeam(pokemon: Pokemon, pokemonCollection: PokemonCollection){
        pokemonCollection.removePokemonFromCollection(pokemon)
        addPokemonToTeam(pokemon)
    }

    fun healAllPokemons(){
        for(pokemon in pokemons){
            pokemon.setCurrentHp(pokemon.getMaxHp())
        }
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

    fun setNewTeamOrder(teamOrder : MutableMap<Int, Pokemon>) {
        val newTeam: ArrayList<Pokemon> = ArrayList()
        for(i in 0 until pokemons.size){
            teamOrder[i]?.let { newTeam.add(it) }
        }
        pokemons = newTeam
    }
}