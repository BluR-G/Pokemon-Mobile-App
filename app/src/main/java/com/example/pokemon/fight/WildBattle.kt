package com.example.pokemon.fight

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pokemon.R
import com.example.pokemon.data.PokemonCreation
import com.example.pokemon.objects.Move
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class WildBattle(pokemonTeam: PokemonTeam, enemyPokemon: Pokemon, activity: FightActivity) : Battle(pokemonTeam, enemyPokemon,activity){
    private lateinit var enemyPokemon: Pokemon

    init {
        this.enemyPokemon = enemyPokemon
        initializeMessage("A wild ${getCurrentEnemyPokemon().getSpecies()} appeared!")
    }

    // Check Target Pokemon status and attack according to status
    override fun checkPokemonStatus(pokemonTarget: Pokemon, pokemonAttacker: Pokemon,
        attackerMove: MoveData, view: View) {
        activity.lifecycleScope.launch(Dispatchers.Main){
            if(pokemonAttacker.isAlive()){
                attackPokemonTarget(pokemonAttacker, pokemonTarget, attackerMove.move)
                updateFightMessage(pokemonAttacker,pokemonTarget,attackerMove)
                if(!pokemonTarget.isAlive() && pokemonTarget == getCurrentEnemyPokemon()){
                    activity.setFightState(-1)
                    // battle Won
                    val previousLevel = currentAllyPokemon.getLevel()
                    // add experience to pokemon and check for possible moves that can be learned
                    addExperience()
                    checkAddToCurrentMoves(previousLevel)
                    displayFinalMessage("You won!")
                } else if(allyPokemonTeam.isTeamDead()){
                    displayFinalMessage("You lost!")
                }
            }
        }
    }

    // Fight between the current pokemon
    override fun fight(view: View, move: MoveData) {
        view.findNavController().navigate(R.id.action_fightFragment_to_fightMenuFragment)
        val enemyMove = pickEnemyRandomMove()
        playPokemonsTurns(move,enemyMove,view)
    }
    // Catch Pokemon
    override fun throwPokeball(view: View) {
        view.findNavController().navigate(R.id.action_bagFragment_to_fightMenuFragment)
        val catchRate = 100.0*(1.0-(enemyPokemon.getCurrentHp()/enemyPokemon.getMaxHp()))
        val catchResult = (0..100).random()

        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text="Catching ${getCurrentEnemyPokemon().getSpecies()}!"
                delay(1000)
                if(catchResult<catchRate) {
                    activity.getBinding().gameMessage.text = "You captured ${getCurrentEnemyPokemon().getSpecies()}!"
                    delay(500)
                    activity.getBinding().gameMessage.text = ""
                    handleTeamCollection()
                    handleCapturedPokemonName()
                } else {
                    activity.getBinding().gameMessage.text="${getCurrentEnemyPokemon().getSpecies()} broke free!"
                    // pokemon attacks if capturing fails
                    enemyAttack()
                }
            }

        }
    }
    // Handle the team and collection when pokemon is captured
    private fun handleTeamCollection() {
        if (allyPokemonTeam.getSize() == 6) {
            // add to collection when pokemon team is full
            allyPokemonCollection.addPokemonToCollection(getCurrentEnemyPokemon())
        } else {
            // add to team when pokemon team is not full
            allyPokemonTeam.addPokemonToTeam(getCurrentEnemyPokemon())
        }
    }

    // Handle the captured pokemon and change its nickname depending on user
    private suspend fun handleCapturedPokemonName() {
        val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.fightNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.action_fightMenuFragment_to_pokemonCaptureFragment)
        activity.getBinding().gameMessage.text = "Enter a nickname for the captured pokemon:"
        withContext(Dispatchers.IO) {
            // waits for user response
            while (newNickname == "") {
            }
            // set nickname to captured pokemon
            capturedSpecies=getCurrentEnemyPokemon().getSpecies()
            getCurrentEnemyPokemon().setNickame(newNickname)
            // reset names
            newNickname=""
            capturedSpecies=""
            run()
        }
    }
}