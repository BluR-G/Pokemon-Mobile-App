package com.example.pokemon.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.pokemon.data.PokemonCreation
import com.example.pokemon.database.PokemonRoomDatabase
import com.example.pokemon.databinding.ActivityMenuBinding
import com.example.pokemon.objects.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class MenuActivity : AppCompatActivity() {
    private var pokemonTeam = PokemonTeam()
    private var pokemonCollection = PokemonCollection()
    private val database by lazy { PokemonRoomDatabase.getDatabase(this)}
    private var pokemon : Pokemon? = null

    lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fromContinue = intent.getBooleanExtra("fromContinue", false)
        if(fromContinue){
            loadPlayerPokemon()
        } else {
            val pokemon = intent.getSerializableExtra("pokemon") as Pokemon
            pokemonTeam.addPokemonToTeam(pokemon)
            for(i in 0..4){
                lifecycleScope.launch(Dispatchers.IO){
                    val rand = Random.nextInt(0, 100).toString()
                    val pokemon = PokemonCreation().createPokemon(rand, "", 5)
                    pokemonTeam.addPokemonToTeam(pokemon)
                }

            }
            for(i in 0..10){
                lifecycleScope.launch(Dispatchers.IO){
                    val rand = Random.nextInt(0, 100).toString()
                    val pokemon = PokemonCreation().createPokemon(rand, "", 5)
                    pokemonCollection.addPokemonToCollection(pokemon)
                }

            }
        }
    }

    private fun loadPlayerPokemon() {
        lifecycleScope.launch(Dispatchers.IO){
            val playerPokemon = database.PokemonDAO().getPlayerPokemons()
            for(i in playerPokemon.indices){
                val pokemonDB = playerPokemon[i]
                val uniqueId = pokemonDB.id
                val pokemonWithMovesDB = database.PokemonDAO().getPokemonMoves(uniqueId)
                val moveDataArr = mutableListOf<MoveData>()
                for(j in pokemonWithMovesDB.indices){
                    val moveDB = database.PokemonDAO().getMove(pokemonWithMovesDB[j].move)
                    val move = Move(moveDB.accuracy, moveDB.power, moveDB.damageClass, moveDB.heal, moveDB.target, moveDB.type)
                    val moveData = MoveData(pokemonWithMovesDB[j].move, pokemonWithMovesDB[j].level_learned_at, move)
                    moveDataArr.add(moveData)
                }
                val pokemonTypesStr = pokemonDB.types
                val pokemonImagesStr = pokemonDB.images
                val pokemonTypesJson = Gson().fromJson(pokemonTypesStr, JsonArray::class.java)
                val pokemonTypes = JsonArrayToStringList(pokemonTypesJson)
                val pokemonImagesJson = Gson().fromJson(pokemonImagesStr, JsonArray::class.java)
                val pokemonImages = JsonArrayToStringList(pokemonImagesJson)
                val moveDataArrList = ArrayList(moveDataArr)
                val pokemon = Pokemon(pokemonDB.pokemon_id, pokemonDB.species,
                    pokemonDB.name, pokemonDB.level, pokemonTypes,
                    pokemonDB.maxHp, pokemonDB.attack, pokemonDB.defense, pokemonDB.specialAttack,
                    pokemonDB.specialDefense, pokemonDB.speed, moveDataArrList, pokemonImages)
                if(pokemonDB.isTeam == 1){
                    pokemonTeam.addPokemonToTeam(pokemon)
                } else {
                    pokemonCollection.addPokemonToCollection(pokemon)
                }
            }
        }
    }

    private fun JsonArrayToStringList(moves: JsonArray): ArrayList<String> {
        val list = ArrayList<String>()
        for (i in 0 until moves.size()) {
            list.add(moves[i].asString)
        }
        return list
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