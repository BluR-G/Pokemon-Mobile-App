package com.example.pokemon.fight

import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
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
    override fun checkPokemonStatus(
        pokemonTarget: Pokemon,
        pokemonAttacker: Pokemon,
        attackerMove: MoveData,
        view: View
    ) {
        if(!isAlive(pokemonTarget)){
            if(pokemonTarget == getCurrentEnemyPokemon()){
                addExperience()
                displayFinalMessage("You won")
            }
        } else {
            if(isAlive(pokemonAttacker)){
                attackPokemonTarget(pokemonTarget, attackerMove.move)
                updateFightMessage(pokemonTarget,pokemonAttacker,attackerMove)
            }
        }
    }
    // Fight between the current pokemon
    override fun fight(view: View, allyMoveData: MoveData) {
        view.findNavController().navigate(R.id.action_fightFragment_to_fightMenuFragment)
        val enemyMove = pickEnemyRandomMove()
        val move = allyMoveData
        checkPokemonStatus(getCurrentEnemyPokemon(), currentAllyPokemon, move, view)
        checkPokemonStatus(currentAllyPokemon, getCurrentEnemyPokemon(), enemyMove, view)
        if(!isAlive(getCurrentEnemyPokemon())){
            addExperience()
            displayFinalMessage("You won")
        } else if(isAllyTeamDead()){
            displayFinalMessage("You lost!")
        }
    }
    // Catch Pokemon
    override fun throwPokeball(view: View) {
        view.findNavController().navigate(R.id.action_bagFragment_to_fightMenuFragment)
        val random = Random()
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