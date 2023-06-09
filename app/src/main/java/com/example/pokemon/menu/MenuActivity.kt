package com.example.pokemon.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pokemon.objects.*
import androidx.activity.result.ActivityResult
import android.app.Activity
import androidx.lifecycle.lifecycleScope
import com.example.pokemon.database.PlayerPokemon
import com.example.pokemon.database.PokemonRoomDatabase
import com.example.pokemon.databinding.ActivityMenuBinding
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuActivity : AppCompatActivity() {
    private var pokemonTeam = PokemonTeam()
    private var pokemonCollection = PokemonCollection()
    private val database by lazy { PokemonRoomDatabase.getDatabase(this)}
    private var pokemon : Pokemon? = null
    private lateinit var userNamePrint : String
    private lateinit var userNameData : String

    lateinit var binding: ActivityMenuBinding
    var getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            pokemonTeam = intent?.getSerializableExtra("pokemonTeam") as PokemonTeam
            pokemonCollection = intent?.getSerializableExtra("pokemonCollection") as PokemonCollection
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fromContinue = intent.getBooleanExtra("fromContinue", false)
        userNameData = intent.getStringExtra("user_welcome_print").toString()
        if(fromContinue){
            userNamePrint = "Welcome back $userNameData"
            loadPlayerPokemon()
        } else {
            userNamePrint = "Welcome $userNameData"
            val pokemon = intent.getSerializableExtra("pokemon") as Pokemon
            pokemonTeam.addPokemonToTeam(pokemon)
        }
    }

    private fun loadPlayerPokemon() {
        lifecycleScope.launch(Dispatchers.IO){
            val playerPokemon = database.PokemonDAO().getPlayerPokemons()
            for(i in playerPokemon.indices){
                createPokemonObj(playerPokemon[i])
            }
        }
    }

    private fun createPokemonObj(pokemonDB: PlayerPokemon) {
        val moveDataArrList = getMoveData(pokemonDB)
        val currentMoveDataArrList = getCurrentMoveData(pokemonDB)
        val pokemonTypes = toArrayList(pokemonDB.types)
        val pokemonImages = toArrayList(pokemonDB.images)
        val pokemon = Pokemon(
            pokemonDB.pokemon_id, pokemonDB.species, pokemonDB.name,
            pokemonDB.level,pokemonDB.baseExperience, pokemonTypes, pokemonDB.maxHp,
            pokemonDB.attack, pokemonDB.defense, pokemonDB.specialAttack,
            pokemonDB.specialDefense, pokemonDB.speed,
            moveDataArrList, pokemonImages
        )
        pokemon.setCurrentMoves(currentMoveDataArrList)
        if (pokemonDB.isTeam == 1) {
            pokemonTeam.addPokemonToTeam(pokemon)
        } else {
            pokemonCollection.addPokemonToCollection(pokemon)
        }
    }
    private fun getCurrentMoveData(pokemonDB : PlayerPokemon): ArrayList<MoveData> {
        val uniqueId = pokemonDB.id
        val pokemonWithCurrentMovesDB = database.PokemonDAO().getPokemonCurrentMoves(uniqueId)
        val currentMoveDataArr = mutableListOf<MoveData>()
        for(i in pokemonWithCurrentMovesDB.indices){
            val moveDB = database.PokemonDAO().getMove(pokemonWithCurrentMovesDB[i].move)
            val move = Move(
                moveDB.accuracy,
                moveDB.power,
                moveDB.damageClass,
                moveDB.heal,
                moveDB.target,
                moveDB.type
            )
            val moveData = MoveData(pokemonWithCurrentMovesDB[i].move, pokemonWithCurrentMovesDB[i].level_learned_at, move)
            currentMoveDataArr.add(moveData)
        }
        return ArrayList(currentMoveDataArr)
    }
    private fun getMoveData(pokemonDB : PlayerPokemon): ArrayList<MoveData> {
        val uniqueId = pokemonDB.id
        val pokemonWithMovesDB = database.PokemonDAO().getPokemonMoves(uniqueId)
        val moveDataArr = mutableListOf<MoveData>()
        for (i in pokemonWithMovesDB.indices) {
            val moveDB = database.PokemonDAO().getMove(pokemonWithMovesDB[i].move)
            val move = Move(
                moveDB.accuracy,
                moveDB.power,
                moveDB.damageClass,
                moveDB.heal,
                moveDB.target,
                moveDB.type
            )
            val moveData =
                MoveData(pokemonWithMovesDB[i].move, pokemonWithMovesDB[i].level_learned_at, move)
            moveDataArr.add(moveData)
        }
        return ArrayList(moveDataArr)
    }

    private fun toArrayList(arrayStr : String): ArrayList<String> {
        val jsonArr = Gson().fromJson(arrayStr, JsonArray::class.java)
        return jsonArrayToStringList(jsonArr)
    }

    private fun jsonArrayToStringList(moves: JsonArray): ArrayList<String> {
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

    fun getUsernamePrint(): String{
        return this.userNamePrint
    }

    fun getUsernameData(): String{
        return this.userNameData
    }
}