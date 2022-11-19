package com.example.pokemon

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.databinding.ActivityFightBinding
import com.example.pokemon.databinding.ActivityMainBinding
import com.example.pokemon.objects.Move
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam

class FightActivity : AppCompatActivity(){
    private lateinit var binding: ActivityFightBinding
    private lateinit var currentPokemon : Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val team = initializeTeam()
        currentPokemon = team.getPokemonTeam()[0]
        binding.pokemonFightText.text=currentPokemon.getName()
//        val adapter = FightPokemonTeamAdapter(team, this)
//        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
//        Log.d("recycler", recyclerView.toString())
//        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
//        recyclerView.adapter = adapter

    }

    fun initializeTeam() : PokemonTeam{
        val pokemonTeam = PokemonTeam()
        val types = arrayOf<String>("fire")
        val moves = ArrayList<MoveData>()
        val images = arrayOf<Drawable>()

        pokemonTeam.addPokemonToTeam(Pokemon("charizard","charizard",36,types,
            200,30,40,100,200,100, moves, images))
        return pokemonTeam
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
}