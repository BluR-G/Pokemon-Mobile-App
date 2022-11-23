package com.example.pokemon.fight

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.databinding.ActivityFightBinding
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam

class FightActivity : AppCompatActivity(){
    private lateinit var binding: ActivityFightBinding
    private lateinit var currentPokemon : Pokemon
    private lateinit var pokemonTeam: PokemonTeam
    private lateinit var trainerBattle: TrainerBattle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.pokemonTeam =intent.getSerializableExtra("pokemonTeam") as PokemonTeam
        this.trainerBattle = TrainerBattle(pokemonTeam, this)
        currentPokemon = this.pokemonTeam.getPokemonTeam()[0]
        binding.pokemonFightText.text=currentPokemon.getName()
        binding.allyPokemonHp.text="HP: ${currentPokemon.getCurrentHp()}/${currentPokemon.getMaxHp()}"

    }

    public fun getBinding() : ActivityFightBinding{
        return this.binding;
    }
    public fun getCurrentPokemon() : Pokemon{
        return this.currentPokemon;
    }
    public fun setCurrentPokemon(pokemon : Pokemon){
        this.currentPokemon = pokemon;
    }
    public fun getPokemonTeam() : PokemonTeam {
        return this.pokemonTeam
    }
    public fun getBattle():TrainerBattle{
        return this.trainerBattle
    }
}