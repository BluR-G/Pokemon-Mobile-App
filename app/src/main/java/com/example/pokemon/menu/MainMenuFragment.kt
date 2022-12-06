package com.example.pokemon.menu

import android.content.Intent
import android.os.Bundle
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainMenuFragment : Fragment() {
    lateinit var menuActivity: MenuActivity
    private val database by lazy { PokemonRoomDatabase.getDatabase(context as MenuActivity)}
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
            database.PokemonDAO().clearTeam()
            database.PokemonDAO().clearCollection()
            for(i in 0 until pokemonTeam.getSize()){
                val teamPositionDB = PokemonTeam(i)
                database.PokemonDAO().insertTeam(teamPositionDB)
                val pokemon = pokemonTeam.getPokemon(i)
                val playerPokemon = createPlayerPokemon(pokemon)
                database.PokemonDAO().insertPokemon(playerPokemon)
                val pokemonInTeam = PokemonInTeam(playerPokemon, teamPositionDB)
                database.PokemonDAO().insertPokemonInTeam(pokemonInTeam)
                addMovesToDB(pokemon.getMoves(), pokemon)
            }
        }

    }

    private suspend fun addMovesToDB(movesData: ArrayList<MoveData>, pokemon: Pokemon) {
        val movesDB = mutableListOf<Move>()

        for(i in 0 until movesData.size){
            val move = movesData[i].move
            val moveDB = Move(movesData[i].moveName, move.getAccuracy(), move.getPower(), move.getDamageClass(), move.getHeal(),move.getTarget(), move.getTypes())
            database.PokemonDAO().insertMove(moveDB)
            movesDB.add(moveDB)
            //val pokemonWithMoves = PokemonWithMoves(pokemon.getId(), movesData[i].moveName, movesData[i].level_learned_at)
            //database.PokemonDAO().insertPokemonWithMoves(pokemonWithMoves)
        }
    }

    private fun createPlayerPokemon(pokemon: Pokemon) : PlayerPokemon {
        return PlayerPokemon(0,
            pokemon.getId(),
            pokemon.getSpecies(),
            pokemon.getName(),
            "getImage",
            pokemon.getExperience(),
            pokemon.getLevel(),
            "getTypes",
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