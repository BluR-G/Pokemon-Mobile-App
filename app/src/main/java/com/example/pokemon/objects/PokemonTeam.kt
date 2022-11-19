package com.example.pokemon.objects

class PokemonTeam {
    private var pokemons: ArrayList<Pokemon> = ArrayList<Pokemon>()

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
        val isSuccess = addPokemonToTeam(pokemon)
        if (!isSuccess){
            //Toast.makeText(this@MainMenuActivity, "Pokemon Team is full", Toast.LENGTH_SHORT).show()
        }
    }

    fun healAllPokemons(){
        for(pokemon in pokemons){
            pokemon.setCurrentHp(pokemon.getMaxHp())
        }
    }

    fun getPokemon(index: Int): Pokemon {
        if (index < pokemons.size){
            return pokemons[index]
        }
        // if index incorrect, returns the last pokemon
        return pokemons[pokemons.size - 1]
    }
}