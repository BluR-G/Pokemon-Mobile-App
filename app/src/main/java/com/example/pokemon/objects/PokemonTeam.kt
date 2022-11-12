package com.example.pokemon.objects

class PokemonTeam {
    private lateinit var pokemons: ArrayList<Pokemon>

    fun addPokemon(pokemon: Pokemon){
        if(pokemons.size < 6){
            this.pokemons.add(pokemon)
        }
    }

    fun healAllPokemons(){
        for(pokemon in pokemons){
            pokemon.setCurrentHp(pokemon.getMaxHp())
        }
    }
}