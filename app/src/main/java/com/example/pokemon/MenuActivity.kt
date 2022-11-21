package com.example.pokemon

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pokemon.databinding.ActivityMainBinding
import com.example.pokemon.databinding.ActivityMenuBinding
import com.example.pokemon.objects.Move
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam

class MenuActivity : AppCompatActivity() {
    private lateinit var pokemonTeam: PokemonTeam
    lateinit var binding: ActivityMenuBinding

    public var getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            pokemonTeam = intent?.getSerializableExtra("pokemonTeam") as PokemonTeam
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        this.pokemonTeam = initializeTeam()
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    fun initializeTeam() : PokemonTeam {
        val pokemonTeam = PokemonTeam()
        val types = arrayOf<String>("fire")
        val moves = ArrayList<MoveData>()
        addMoves(moves)
        val pikachuMoves = ArrayList<MoveData>()
        addPikachuMoves(pikachuMoves)
        val images = arrayOf<Drawable>()

        pokemonTeam.addPokemonToTeam(
            Pokemon("charizard","charizard",36,types,
                200,30,40,100,200,100, moves, images)
        )
        pokemonTeam.addPokemonToTeam(
            Pokemon("pikachu","pikachu",36,types,
                60,30,40,100,200,100, pikachuMoves, images)
        )
        pokemonTeam.addPokemonToTeam(
            Pokemon("raichu","raichu",36,types,
                60,30,40,100,200,100, moves, images)
        )
        pokemonTeam.addPokemonToTeam(
            Pokemon("garyados","gyarados",36,types,
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
    public fun getPokemonTeam () : PokemonTeam {
        return this.pokemonTeam
    }
    private fun addMoves(moves: ArrayList<MoveData>){
        val types = arrayOf("fire")
        val move1 = Move(100,40,"other","special", "", 40, types)
        val move2 = Move(100,70,"other","special", "", 40, types)
        val move3 = Move(100,10,"other","special", "", 40, types)
        val move4 = Move(100,35,"other","special", "", 40, types)
        moves.add(MoveData("ember", 5,move1))
        moves.add(MoveData("whip", 5,move2))
        moves.add(MoveData("tackle", 5,move3))
        moves.add(MoveData("sleep", 5,move4))
    }
    private fun addPikachuMoves(moves: ArrayList<MoveData>){
        val types= arrayOf("electric")
        val move = Move(100,40,"other","special", "", 40, types)
        moves.add(MoveData("Thunderbolt", 5,move))
        moves.add(MoveData("Swift tackle", 5,move))
        moves.add(MoveData("Roll", 5,move))
        moves.add(MoveData("Static", 5,move))
    }
}