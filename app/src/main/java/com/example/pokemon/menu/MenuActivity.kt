package com.example.pokemon.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.pokemon.data.PokemonCreation
import com.example.pokemon.databinding.ActivityMenuBinding
import com.example.pokemon.objects.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class MenuActivity : AppCompatActivity() {
    private var pokemonTeam = PokemonTeam()
    private var pokemonCollection = PokemonCollection()
    private var pokemon : Pokemon? = null

    lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pokemon = intent.getSerializableExtra("pokemon") as Pokemon
        pokemonTeam.addPokemonToTeam(pokemon)
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Button Disabled", Toast.LENGTH_SHORT).show()
    }


    fun getTeam() : PokemonTeam {
        return this.pokemonTeam
    }

    fun getCollect() : PokemonCollection {
        return this.pokemonCollection
    }

    fun getPokemon() : Pokemon? {
        return this.pokemon
    }

    fun setPokemon(pokemon : Pokemon) {
        this.pokemon = pokemon
    }


}