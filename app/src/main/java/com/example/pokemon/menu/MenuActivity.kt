package com.example.pokemon.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokemon.databinding.ActivityMenuBinding
import com.example.pokemon.objects.*

class MenuActivity : AppCompatActivity() {
    var pokemonTeam = PokemonTeam()
    var pokemonCollection = PokemonCollection()

    lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pokemon = intent.getSerializableExtra("pokemon") as Pokemon
        pokemonTeam.addPokemonToTeam(pokemon)
    }


    fun getTeam() : PokemonTeam {
        return this.pokemonTeam
    }

    fun getCollect() : PokemonCollection {
        return this.pokemonCollection
    }


}