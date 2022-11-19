package com.example.pokemon

import android.graphics.drawable.Drawable
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
        pokemonTeam = initializeTeam()
    }

    private fun initializeTeam(): PokemonTeam {
        val types1 = arrayOf<String>("bubble")
        val types2 = arrayOf<String>("fire")
        val pokeType = arrayOf<String>("fire")
        val moves = ArrayList<MoveData>()
        val images = arrayOf<Drawable>()
        val move1 : Move = Move(100, 40, "OTHER", "SPECIAL", "", 30, types1)
        val move2 : Move = Move(100, 40, "SPECIAL", "OPPONENT", "", 30, types2)
        moves.add(MoveData("bubble", 30, move1))
        moves.add(MoveData("ember", 10, move2))
        pokemonTeam.addPokemonToTeam(
            Pokemon("charizard","charizard",13,pokeType,
                200,30,40,100,200,100, moves, images)
        )

        pokemonTeam.addPokemonToTeam(
            Pokemon("squitle","squitle",10,arrayOf<String>("water"),
                44,48,65,50,200,100, moves, images)
        )

        return pokemonTeam
    }

    public fun getBind(): ActivityMenuBinding {
        return this.binding
    }

    public fun getTeam() : PokemonTeam {
        return this.pokemonTeam
    }

    public fun getCollect() : PokemonCollection {
        return this.pokemonCollection
    }


}