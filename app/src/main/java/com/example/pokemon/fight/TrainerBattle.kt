package com.example.pokemon.fight

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.pokemon.R
import com.example.pokemon.objects.Move
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class TrainerBattle(pokemonTeam: PokemonTeam, enemyTeam: PokemonTeam, activity: FightActivity) : Battle(pokemonTeam, enemyTeam.getPokemon(0),activity) {
    private lateinit var enemyTeam: PokemonTeam
    private var count = 0

    init {
        this.enemyTeam = enemyTeam
        setCurrentEnemyPokemon(enemyTeam.getPokemonTeam()[0])
        initializeMessage()
    }
    private fun initializeMessage(){
        activity.getBinding().enemyPokemonText.text=getCurrentEnemyPokemon().getSpecies()
        activity.getBinding().enemyPokemonHp.text="HP: ${getCurrentEnemyPokemon().getCurrentHp()}/${getCurrentEnemyPokemon().getMaxHp()}"
        activity.getBinding().enemyLevel.text = "lv.${getCurrentEnemyPokemon().getLevel()}"
    }
    public override fun checkPokemonStatus(pokemonTarget: Pokemon, pokemonAttacker: Pokemon, attackerMove : MoveData, view : View){
        if(pokemonAttacker.isAlive()){
            attackPokemonTarget(pokemonAttacker,pokemonTarget,attackerMove.move)
            if(enemyTeam.isTeamDead()){
                // Double experience
                addExperience()
                displayFinalMessage("You won!")
            } else if(allyPokemonTeam.isTeamDead()){
                displayFinalMessage("You lost!")
            }
            if(!getCurrentEnemyPokemon().isAlive() && pokemonTarget == getCurrentEnemyPokemon()){
                swapEnemy()
            }
            updateFightMessage(pokemonAttacker,pokemonTarget,attackerMove)
        }

    }
    // Fight between the current pokemon
    public override fun fight(view: View, move : MoveData){
        view.findNavController().navigate(R.id.action_fightFragment_to_fightMenuFragment)
        val enemyMove = pickEnemyRandomMove()
        playPokemonsTurns(move,enemyMove,view)

    }

    // Attempt to catch wild pokemon
    public override fun throwPokeball(view:View){
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text="You cannot catch a trainer's pokemon!"
                delay(2000)
                activity.getBinding().gameMessage.text=""
            }
        }

    }
    private fun swapEnemy(){
        val enemyTeam = enemyTeam.getPokemonTeam()
        if(count<enemyTeam.size-1){
            count++
            setCurrentEnemyPokemon(enemyTeam[count])
            activity.lifecycleScope.launch(Dispatchers.Default){
                withContext(Dispatchers.Main){
                    delay(3000)
                    activity.getBinding().gameMessage.text="Enemy swapping to ${getCurrentEnemyPokemon().getName()}!"
                    initializeMessage()
                    delay(1000)
                    activity.getBinding().gameMessage.text=""
                }
            }
        }
    }
}