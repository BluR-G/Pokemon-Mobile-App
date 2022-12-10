package com.example.pokemon.fight

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Base64
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
        initializeMessage("You were challenged by a trainer!")
    }

    private fun updateEnemyPokemon(){
        activity.getBinding().enemyPokemonFront.setImageBitmap(getImage(getCurrentEnemyPokemon(), 0))
        activity.getBinding().enemyPokemonText.text=getCurrentEnemyPokemon().getSpecies()
        activity.getBinding().enemyPokemonHp.text="HP: ${getCurrentEnemyPokemon().getCurrentHp()}/${getCurrentEnemyPokemon().getMaxHp()}"
        activity.getBinding().enemyLevel.text = "lv.${getCurrentEnemyPokemon().getLevel()}"
    }
    // Check Target Pokemon status and attack according to status
    public override fun checkPokemonStatus(pokemonTarget: Pokemon, pokemonAttacker: Pokemon, attackerMove : MoveData, view : View){
        if(pokemonAttacker.isAlive()){
            attackPokemonTarget(pokemonAttacker,pokemonTarget,attackerMove.move)
            if(!getCurrentEnemyPokemon().isAlive() || enemyTeam.isTeamDead()){
                activity.lifecycleScope.launch(Dispatchers.Main){
                    activity.setFightState(-1)
                    val previousLevel = currentAllyPokemon.getLevel()
                    addExperience()
                    checkAddToCurrentMoves(previousLevel)
                    activity.setFightState(0)
                }
                if(enemyTeam.isTeamDead()) {
                    displayFinalMessage("You won!")
                    //pauseMusic() debug
                }
            } else if(allyPokemonTeam.isTeamDead()){
                displayFinalMessage("You lost!")
                //pauseMusic() debug
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
    // Swap enemy when previous enemy dies
    private fun swapEnemy(){
        val enemyTeam = enemyTeam.getPokemonTeam()
        if(count<enemyTeam.size-1){
            count++
            setCurrentEnemyPokemon(enemyTeam[count])
            activity.lifecycleScope.launch(Dispatchers.Default){
                withContext(Dispatchers.Main){
                    delay(3000)
                    activity.getBinding().gameMessage.text="Enemy swapping to ${getCurrentEnemyPokemon().getName()}!"
                    updateEnemyPokemon()
                    delay(1000)
                    activity.getBinding().gameMessage.text=""
                }
            }
        }
    }
}