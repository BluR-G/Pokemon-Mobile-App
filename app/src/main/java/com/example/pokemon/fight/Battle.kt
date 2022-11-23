package com.example.pokemon.fight

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.pokemon.MenuActivity
import com.example.pokemon.R
import com.example.pokemon.objects.Move
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

abstract class Battle {
    public lateinit var allyPokemonTeam: PokemonTeam
    public lateinit var currentAllyPokemon : Pokemon
    private lateinit var currentEnemyPokemon: Pokemon
    public lateinit var activity: FightActivity

    constructor(pokemonTeam: PokemonTeam, activity: FightActivity) {
        this.activity = activity
        this.allyPokemonTeam = pokemonTeam
        this.currentAllyPokemon = pokemonTeam.getPokemonTeam()[0]
    }
    public fun setCurrentEnemyPokemon(pokemon: Pokemon){
        this.currentEnemyPokemon = pokemon
    }
    public fun getCurrentEnemyPokemon(): Pokemon{
        return this.currentEnemyPokemon
    }

    abstract fun checkPokemonStatus(pokemonTarget: Pokemon, pokemonAttacker: Pokemon, attackerMove : MoveData, view : View)
    abstract fun fight(view: View, allyMoveData : MoveData)
    abstract fun throwPokeball(view: View)

    // Swap pokemon
    public fun swapPokemon(view: View, pokemon: Pokemon){
        lateinit var enemyMove : MoveData
        if(isAlive(pokemon)){
            if(activity.getCurrentPokemon() != pokemon){
                currentAllyPokemon = pokemon
                activity.setCurrentPokemon(pokemon)
                enemyMove = pickEnemyRandomMove()
                updateSwapMessage(pokemon, currentEnemyPokemon,enemyMove, view)
            } else {
                Log.d("swap", "cannot swap")
            }
        }
    }
    // Apply potion effect to pokemon
    public fun usePotion(view: View, pokemon: Pokemon){
        heal(pokemon)
        updateHealMessage(view, pokemon)
    }
    public fun displayFinalMessage(message:String){
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text=message
                delay(3000)
                run()
            }
        }
    }
    // Update the messages after fight sequence
    public fun updateFightMessage(firstAttacked: Pokemon, secondAttacked: Pokemon, firstMove: MoveData){
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text="${secondAttacked.getName()} used ${firstMove.moveName}!"
                Log.d("fight", "${secondAttacked.getName()} used ${firstMove.moveName}!")
                delay(1500)
                if(firstAttacked == currentAllyPokemon){
                    activity.getBinding().allyPokemonHp.text="HP:${firstAttacked.getCurrentHp()}/${firstAttacked.getMaxHp()}"
                } else {
                    activity.getBinding().enemyPokemonHp.text="HP:${firstAttacked.getCurrentHp()}/${firstAttacked.getMaxHp()}"
                }
                activity.getBinding().gameMessage.text=""
            }
        }
    }
    // Updates game message when current pokemon is swapped
    public fun updateSwapMessage(pokemon: Pokemon, enemyPokemon: Pokemon, enemyMove: MoveData, view: View){
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text="Swapping to ${pokemon.getName()} !"
                view.findNavController().navigate(R.id.action_fightPokemonTeamFragment_to_fightMenuFragment)
                delay(1000)
                activity.getBinding().allyPokemon.text=pokemon.getName()
                activity.getBinding().allyPokemonHp.text="HP:${currentAllyPokemon.getCurrentHp()}/${currentAllyPokemon.getMaxHp()} HP"
                activity.getBinding().allyLevel.text = "lv.${currentAllyPokemon.getLevel()}"
            }
            delay(1000)
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text=""
                activity.getBinding().gameMessage.text="${enemyPokemon.getName()} used ${enemyMove.moveName}!"
                delay(1500)
                attackPokemonTarget(currentAllyPokemon,enemyMove.move,view)
                activity.getBinding().allyPokemonHp.text="HP:${currentAllyPokemon.getCurrentHp()}/${currentAllyPokemon.getMaxHp()} HP"
                activity.getBinding().gameMessage.text=""
            }
        }
    }
    // Updates game message when current pokemon is healed
    public fun updateHealMessage(view: View, pokemon: Pokemon){
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                view.findNavController().navigate(R.id.action_fightPokemonTeamFragment_to_fightMenuFragment)
                // Show current hp
                activity.getBinding().gameMessage.text="${pokemon.getName()} has now ${pokemon.getCurrentHp()} HP!"
                activity.getBinding().allyPokemonHp.text="${currentAllyPokemon.getCurrentHp()}/${currentAllyPokemon.getMaxHp()} HP"
                delay(1000)
                activity.getBinding().gameMessage.text=""
            }
            delay(1000)
            withContext(Dispatchers.Main){
                var enemyMove = pickEnemyRandomMove()
                // Attacks swapped Pokemon
                attackPokemonTarget(currentAllyPokemon,enemyMove.move,view)
                activity.getBinding().gameMessage.text="${currentEnemyPokemon.getName()} used ${enemyMove.moveName}!"
                delay(1000)
                activity.getBinding().gameMessage.text=""
                delay(1500)
                // Updates pokemon text
                activity.getBinding().allyPokemonHp.text="${currentAllyPokemon.getCurrentHp()}/${currentAllyPokemon.getMaxHp()} HP"
            }
        }
    }
    // Heal Pokemon
    public fun heal(pokemon: Pokemon){
        val potionHeal = 20
        if(isAlive(pokemon)){
            var healHp = pokemon.getCurrentHp()+ potionHeal
            if(healHp<pokemon.getMaxHp()){
                pokemon.setCurrentHp(healHp)
            } else {
                pokemon.setCurrentHp(pokemon.getMaxHp())
            }
        }
    }
    // Exits the current activity and sends back team to menu
    public fun run(){
        val intent = Intent(activity, MenuActivity::class.java)
        intent.putExtra("pokemonTeam", activity.getPokemonTeam())
        activity.setResult(Activity.RESULT_OK, intent)
        activity.finish()
    }
    // Generates the next enemy move
    public fun pickEnemyRandomMove() : MoveData {
        val random = Random()
        val moveNumber = random.nextInt(4)
        val enemyMove = currentEnemyPokemon.getMoves()[moveNumber]
        return enemyMove
    }
    // Attack pokemon target with move
    public fun attackPokemonTarget(target: Pokemon, move: Move, view: View){
        val targetHp = target.getCurrentHp() - move.getPower()
        if (targetHp < 0){
            target.setCurrentHp(0)
            if(target == currentAllyPokemon){
                view.findNavController().navigate(R.id.action_fightMenuFragment_to_fightPokemonTeamFragment)
            }
        } else {
            target.setCurrentHp(targetHp)
        }

    }
    // Checks if ally full team is dead
    public fun isAllyTeamDead():Boolean{
        return isTeamDead(allyPokemonTeam)
    }
    // Check if team is pokemon dead
    public fun isTeamDead(pokemonTeam: PokemonTeam):Boolean{
        var check = false
        for(pokemon in pokemonTeam.getPokemonTeam()){
            if (pokemon.getCurrentHp() == 0) {
                check = true
            }
            else {
                return false
            }
        }
        return check
    }
    // Check if pokemon is alive
    public fun isAlive(pokemon: Pokemon):Boolean{
        if(pokemon.getCurrentHp()==0){
            return false
        }
        return true
    }
}