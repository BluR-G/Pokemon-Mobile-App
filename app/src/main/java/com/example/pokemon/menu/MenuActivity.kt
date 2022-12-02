package com.example.pokemon.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pokemon.databinding.ActivityMenuBinding
import com.example.pokemon.objects.*
import androidx.activity.result.ActivityResult
import android.app.Activity

class MenuActivity : AppCompatActivity() {
    var pokemonTeam = PokemonTeam()
    var pokemonCollection = PokemonCollection()

    lateinit var binding: ActivityMenuBinding
    public var getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            pokemonTeam = intent?.getSerializableExtra("pokemonTeam") as PokemonTeam
        }
    }
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


}