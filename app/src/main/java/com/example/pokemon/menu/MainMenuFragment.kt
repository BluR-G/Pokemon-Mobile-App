package com.example.pokemon.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.pokemon.FightActivity
import com.example.pokemon.R
import com.example.pokemon.database.*
import com.example.pokemon.databinding.FragmentMainMenuBinding
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainMenuFragment : Fragment() {
    lateinit var menuActivity: MenuActivity
    private val database by lazy { PokemonRoomDatabase.getDatabase(menuActivity)}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        menuActivity = context as MenuActivity
        val binding = FragmentMainMenuBinding.inflate(layoutInflater)
        binding.goToTeam.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_mainMenuFragment_to_teamFragment)
        }
        binding.goToPokeCenter.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainMenuFragment_to_pokeCenterFragment)
        }
        binding.goToTrainerBattle.setOnClickListener { view: View ->
            val intent = Intent(activity, FightActivity::class.java)
            startActivity(intent)
        }
        binding.goToWildBattle.setOnClickListener{ view: View ->
            val intent = Intent(activity, FightActivity::class.java)
            startActivity(intent)
        }
        binding.save.setOnClickListener {
            saveToDatabase()
            Toast.makeText(activity, "Game saved", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    private fun saveToDatabase() {
        lifecycleScope.launch(Dispatchers.IO){
            val pokemonTeam = menuActivity.getTeam()
            val pokemonCollection = menuActivity.getCollect()
            database.PokemonDAO().clearPlayerPokemons()
            database.PokemonDAO().clearPokemonWithMoves()
            database.PokemonDAO().clearPokemonWithCurrentMoves()
//            database.PokemonDAO().clearTeam()
//            database.PokemonDAO().clearCollection()
            var uniqueId = 0
            for(i in 0 until pokemonTeam.getSize()){
                //val teamPositionDB = PokemonTeam(i)
                //database.PokemonDAO().insertTeam(teamPositionDB)
                val pokemon = pokemonTeam.getPokemon(i)
                val playerPokemon = createPlayerPokemon(pokemon, i, 1, uniqueId)
                database.PokemonDAO().insertPokemon(playerPokemon)
                //val id = database.PokemonDAO().getPlayerPokemonId(playerPokemon.position, playerPokemon.isTeam, playerPokemon.pokemon_id, playerPokemon.name)

                //val pokemonInTeam = PokemonInTeam(playerPokemon, teamPositionDB)
                //database.PokemonDAO().insertPokemonInTeam(pokemonInTeam)
                addMovesToDB(pokemon.getMoves(), uniqueId)
                uniqueId++
            }
            for(i in 0 until pokemonCollection.getSize()){
                //val collectionPosition = PokemonCollection(i)
                //database.PokemonDAO().insertCollection(collectionPosition)
                val pokemon = pokemonCollection.getPokemon(i)
                val playerPokemon = createPlayerPokemon(pokemon, i, 0, uniqueId)
                database.PokemonDAO().insertPokemon(playerPokemon)
                //val id = database.PokemonDAO().getPlayerPokemonId(playerPokemon.position, playerPokemon.isTeam, playerPokemon.pokemon_id, playerPokemon.name)
                //val pokemonInCollection = PokemonInCollection(playerPokemon, collectionPosition)
                //database.PokemonDAO().insertPokemonInCollection(pokemonInCollection)
                addMovesToDB(pokemon.getMoves(), uniqueId)
                uniqueId++
            }
        }
    }

    private fun addMovesToDB(movesData: ArrayList<MoveData>, id: Int) {
        //val movesDB = mutableListOf<Move>()

        for(i in 0 until movesData.size){
            val move = movesData[i].move
            val moveDB = Move(movesData[i].moveName, move.getAccuracy(), move.getPower(), move.getDamageClass(), move.getHeal(),move.getTarget(), move.getTypes())
            database.PokemonDAO().insertMove(moveDB)
            val pokemonWithMoves = PokemonWithMoves(id, movesData[i].moveName, movesData[i].level_learned_at)
            //movesDB.add(moveDB)
            //val pokemonWithMoves = PokemonWithMoves(pokemon.getId(), movesData[i].moveName, movesData[i].level_learned_at)
            database.PokemonDAO().insertPokemonWithMoves(pokemonWithMoves)
        }
        //val playerPokemonMovePair = PlayerPokemonMovePair(pokemon, movesDB)

//        database.PokemonDAO().insertPokemonWithMoves()
    }

    private fun createPlayerPokemon(pokemon: Pokemon, position: Int, isTeam: Int, uniqueId: Int) : PlayerPokemon {
        val images = pokemon.getImages()
        val imagesToJsonStr = Gson().toJson(images)
        val types = pokemon.getTypes()
        val movesToJsonStr = Gson().toJson(types)
        return PlayerPokemon(
            uniqueId,
            position,
            isTeam,
            pokemon.getId(),
            pokemon.getSpecies(),
            pokemon.getName(),
            imagesToJsonStr,
            pokemon.getExperience(),
            pokemon.getLevel(),
            movesToJsonStr,
            pokemon.getMaxHp(),
            pokemon.getCurrentHp(),
            pokemon.getAttack(),
            pokemon.getDefense(),
            pokemon.getSpecialAttack(),
            pokemon.getSpecialDefense(),
            pokemon.getSpeed()
        )
    }


}