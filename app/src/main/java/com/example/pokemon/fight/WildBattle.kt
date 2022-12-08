package com.example.pokemon.fight

import android.graphics.drawable.Drawable
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
        initializeMessage()

    }

    private fun initializeMessage(){
        activity.getBinding().enemyPokemonText.text=getCurrentEnemyPokemon().getSpecies()
        activity.getBinding().enemyPokemonHp.text="HP: ${getCurrentEnemyPokemon().getCurrentHp()}/${getCurrentEnemyPokemon().getMaxHp()}"
        activity.getBinding().enemyLevel.text = "lv.${getCurrentEnemyPokemon().getLevel()}"
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                delay(200)
                activity.getBinding().gameMessage.text="A wild ${getCurrentEnemyPokemon().getSpecies()} appeared!"
                delay(1500)
                activity.getBinding().gameMessage.text=""
            }
        }
    }

    // Check Target Pokemon status and attack according to status
    override fun checkPokemonStatus(pokemonTarget: Pokemon, pokemonAttacker: Pokemon,
        attackerMove: MoveData, view: View) {
        activity.lifecycleScope.launch(Dispatchers.Main){
            if(pokemonAttacker.isAlive()){
                attackPokemonTarget(pokemonAttacker, pokemonTarget, attackerMove.move)
                updateFightMessage(pokemonAttacker,pokemonTarget,attackerMove)
                if(!pokemonTarget.isAlive() && pokemonTarget == getCurrentEnemyPokemon()){
                    // Battle Won
                    val previousLevel = currentAllyPokemon.getLevel()
                    // Double experience
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
                    if(allyPokemonTeam.getSize()==6){
                        allyPokemonCollection.addPokemonToCollection(getCurrentEnemyPokemon())
                    } else {
                        allyPokemonTeam.addPokemonToTeam(getCurrentEnemyPokemon())
                    }
                    run()
                } else {
                    activity.getBinding().gameMessage.text="${getCurrentEnemyPokemon().getSpecies()} broke free!"
                    enemyAttack()
                }
            }

        }
    }
}