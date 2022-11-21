package com.example.pokemon.fight

import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.R
import com.example.pokemon.databinding.ActivityFightBinding
import com.example.pokemon.objects.Move
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam

class FightActivity : AppCompatActivity(){
    private lateinit var binding: ActivityFightBinding
    private lateinit var currentPokemon : Pokemon
    private lateinit var pokemonTeam: PokemonTeam
    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.pokemonTeam =intent.getSerializableExtra("pokemonTeam") as PokemonTeam
        this.game = Game(pokemonTeam, this)
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
    public fun getGame():Game{
        return this.game
    }
}