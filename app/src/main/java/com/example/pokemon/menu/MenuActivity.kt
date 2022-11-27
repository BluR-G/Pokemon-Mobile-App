package com.example.pokemon.menu

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
//        pokemonTeam = initializeTeam()
//        pokemonCollection = initializeCollection()
    }
//    private fun initializeCollection(): PokemonCollection {
//        val pokemonCollection = PokemonCollection()
//        val types = ArrayList<String>()
//        types.add("fire")
//        val moves = ArrayList<MoveData>()
//        addMoves(moves)
//        val pikachuMoves = ArrayList<MoveData>()
//        addPikachuMoves(pikachuMoves)
//        val images = ArrayList<String>()
//
//        pokemonCollection.addPokemonToCollection(
//            Pokemon("charizard","charizard",36,types,
//                200,30,40,100,200,100, moves, images)
//        )
//        pokemonCollection.addPokemonToCollection(
//            Pokemon("pikachu","pikachu",36,types,
//                60,30,40,100,200,100, pikachuMoves, images)
//        )
//        pokemonCollection.addPokemonToCollection(
//            Pokemon("raichu","raichu",36,types,
//                60,30,40,100,200,100, moves, images)
//        )
//        pokemonCollection.addPokemonToCollection(
//            Pokemon("charizard","charizard",36,types,
//                200,30,40,100,200,100, moves, images)
//        )
//        pokemonCollection.addPokemonToCollection(
//            Pokemon("pikachu","pikachu",36,types,
//                60,30,40,100,200,100, pikachuMoves, images)
//        )
//        pokemonCollection.addPokemonToCollection(
//            Pokemon("raichu","raichu",36,types,
//                60,30,40,100,200,100, moves, images)
//        )
//        pokemonCollection.addPokemonToCollection(
//            Pokemon("charizard","charizard",36,types,
//                200,30,40,100,200,100, moves, images)
//        )
//        pokemonCollection.addPokemonToCollection(
//            Pokemon("pikachu","pikachu",36,types,
//                60,30,40,100,200,100, pikachuMoves, images)
//        )
//        pokemonCollection.addPokemonToCollection(
//            Pokemon("raichu","raichu",36,types,
//                60,30,40,100,200,100, moves, images)
//        )
//
//        return pokemonCollection
//    }
//
//    private fun initializeTeam(): PokemonTeam {
//        val pokemonTeam = PokemonTeam()
//        val types = ArrayList<String>()
//        types.add("fire")
//        val moves = ArrayList<MoveData>()
//        addMoves(moves)
//        val pikachuMoves = ArrayList<MoveData>()
//        addPikachuMoves(pikachuMoves)
//        val images = ArrayList<String>()
//
//        pokemonTeam.addPokemonToTeam(
//            Pokemon("charizard","charizard",36,types,
//                200,30,40,100,200,100, moves, images)
//        )
//        pokemonTeam.addPokemonToTeam(
//            Pokemon("pikachu","pikachu",36,types,
//                60,30,40,100,200,100, pikachuMoves, images)
//        )
//        pokemonTeam.addPokemonToTeam(
//            Pokemon("raichu","raichu",36,types,
//                60,30,40,100,200,100, moves, images)
//        )
//        pokemonTeam.addPokemonToTeam(
//            Pokemon("garyados","gyarados",36,types,
//                60,30,40,100,200,100, moves, images)
//        )
//        pokemonTeam.addPokemonToTeam(
//            Pokemon("raichu","zekrom",36,types,
//                60,30,40,100,200,100, moves, images)
//        )
//        pokemonTeam.addPokemonToTeam(
//            Pokemon("garyados","reshiram",36,types,
//                60,30,40,100,200,100, moves, images)
//        )
//
//        return pokemonTeam
//    }
//    private fun addMoves(moves: ArrayList<MoveData>){
//        val types = "fire"
//        val move1 = Move(100,40,"other","special", "", 40, types)
//        moves.add(MoveData("ember", 5,move1))
//        moves.add(MoveData("whip", 5,move1))
//        moves.add(MoveData("tackle", 5,move1))
//        moves.add(MoveData("sleep", 5,move1))
//    }
//    private fun addPikachuMoves(moves: ArrayList<MoveData>){
//        val types= "electric"
//        val move = Move(100,40,"other","special", "", 40, types)
//        moves.add(MoveData("Thunderbolt", 5,move))
//        moves.add(MoveData("Swift tackle", 5,move))
//        moves.add(MoveData("Roll", 5,move))
//        moves.add(MoveData("Static", 5,move))
//    }

    public fun getTeam() : PokemonTeam {
        return this.pokemonTeam
    }

    public fun getCollect() : PokemonCollection {
        return this.pokemonCollection
    }


}