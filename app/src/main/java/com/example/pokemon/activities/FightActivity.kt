package com.example.pokemon.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemon.databinding.ActivityFightBinding
import com.example.pokemon.objects.Move
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam

class FightActivity : AppCompatActivity(){
    private lateinit var binding: ActivityFightBinding
    private lateinit var currentPokemon : Pokemon
    private lateinit var pokemonTeam: PokemonTeam

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.pokemonTeam = initializeTeam()
        currentPokemon = this.pokemonTeam.getPokemonTeam()[0]
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
        addMoves(moves)
        val images = arrayOf<Drawable>()

        pokemonTeam.addPokemonToTeam(Pokemon("charizard","charizard",36,types,
            200,30,40,100,200,100, moves, images))
        pokemonTeam.addPokemonToTeam(
            Pokemon("pikachu","pikachu",36,types,
                60,30,40,100,200,100, moves, images)
        )
        pokemonTeam.addPokemonToTeam(
            Pokemon("raichu","raichu",36,types,
                60,30,40,100,200,100, moves, images)
        )
        pokemonTeam.addPokemonToTeam(
            Pokemon("garyados","garyados",36,types,
                60,30,40,100,200,100, moves, images)
        )
        pokemonTeam.addPokemonToTeam(
            Pokemon("raichu","zekrom",36,types,
                60,30,40,100,200,100, moves, images)
        )
        pokemonTeam.addPokemonToTeam(
            Pokemon("garyados","reshiram",36,types,
                60,30,40,100,200,100, moves, images)
        )

        return pokemonTeam
    }
    private fun addMoves(moves: ArrayList<MoveData>){
        val types = arrayOf("fire")
        val move1 = Move(100,40,"other","special", "", 40, types)
        moves.add(MoveData("ember", 5,move1))
        moves.add(MoveData("whip", 5,move1))
        moves.add(MoveData("tackle", 5,move1))
        moves.add(MoveData("sleep", 5,move1))
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
}