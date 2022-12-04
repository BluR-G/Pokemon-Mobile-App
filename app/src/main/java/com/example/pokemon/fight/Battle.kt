package com.example.pokemon.fight

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pokemon.MenuActivity
import com.example.pokemon.R
import com.example.pokemon.objects.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

abstract class Battle {
    public lateinit var allyPokemonTeam: PokemonTeam
    public lateinit var allyPokemonCollection: PokemonCollection
    public lateinit var currentAllyPokemon : Pokemon
    private lateinit var currentEnemyPokemon: Pokemon

    public lateinit var activity: FightActivity

    constructor(pokemonTeam: PokemonTeam, currentEnemyPokemon: Pokemon,activity: FightActivity) {
        this.activity = activity
        this.allyPokemonTeam = pokemonTeam
        this.allyPokemonCollection = activity.getPokemonCollection()
        this.currentAllyPokemon = pokemonTeam.getPokemonTeam()[0]
        this.currentEnemyPokemon = currentEnemyPokemon
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

    public fun playPokemonsTurns(move:MoveData, enemyMove: MoveData, view: View){
        if(currentAllyPokemon.getSpeed() > getCurrentEnemyPokemon().getSpeed()){
            checkPokemonStatus(currentAllyPokemon, getCurrentEnemyPokemon(), enemyMove, view)
            checkPokemonStatus(getCurrentEnemyPokemon(), currentAllyPokemon, move, view)
        } else {
            checkPokemonStatus(getCurrentEnemyPokemon(), currentAllyPokemon, move, view)
            checkPokemonStatus(currentAllyPokemon, getCurrentEnemyPokemon(), enemyMove, view)
        }
    }
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
                displayCannotSwapMessage()
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
    private fun displayCannotSwapMessage(){
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text="Cannot swap to to the same pokemon!"
                delay(1000)
                activity.getBinding().gameMessage.text = ""
            }
        }
    }
    // Update the messages after fight sequence
    public fun updateFightMessage(firstAttacked: Pokemon, secondAttacked: Pokemon, firstMove: MoveData){
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text="${secondAttacked.getName()} used ${firstMove.moveName}!"
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
               //activity.getBinding().allyPokemonSprite.setImageBitmap(activity.getImage(currentAllyPokemon,1))
                activity.getBinding().allyPokemon.text=pokemon.getName()
                activity.getBinding().allyPokemonHp.text="HP:${currentAllyPokemon.getCurrentHp()}/${currentAllyPokemon.getMaxHp()} HP"
                activity.getBinding().allyLevel.text = "lv.${currentAllyPokemon.getLevel()}"
            }
            delay(1000)
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text=""
                activity.getBinding().gameMessage.text="${enemyPokemon.getName()} used ${enemyMove.moveName}!"
                delay(1500)
                attackPokemonTarget(currentAllyPokemon, currentEnemyPokemon, enemyMove.move)
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
            enemyAttack()
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
        intent.putExtra("pokemonCollection", activity.getPokemonCollection())
        activity.setResult(Activity.RESULT_OK, intent)
        activity.finish()
    }

    // Generates the next enemy move
    public fun pickEnemyRandomMove(): MoveData {
        val random = Random()
        val moveNumber = random.nextInt(4)
        return currentEnemyPokemon.getMoves()[moveNumber]
    }
    // Attack pokemon target with move
    public fun attackPokemonTarget(attacker: Pokemon, target: Pokemon, move: Move){
        val moveChance = (1..100).random()
        var moveDamage = 0
        if(move.getDamageClass() == "PHYSICAL"){
            moveDamage = calculateDamage(attacker, target, move,
                                         attacker.getAttack(), target.getDefense())
        } else if(move.getDamageClass() == "SPECIAL") {
            moveDamage = calculateDamage(attacker, target, move,
                                         attacker.getSpecialAttack(), target.getSpecialDefense())
        }

        if(move.getAccuracy()>moveChance){
            val targetHp = target.getCurrentHp() - moveDamage
            if (targetHp < 0){
                target.setCurrentHp(0)

                if(target == currentAllyPokemon){
                    val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.fightNavHostFragment) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_fightMenuFragment_to_fightPokemonTeamFragment)
                }
            } else {
                target.setCurrentHp(targetHp)
            }
        } else {
            activity.lifecycleScope.launch(Dispatchers.Default){
                withContext(Dispatchers.Main){
                    activity.getBinding().gameMessage.text="${attacker.getName()} missed!"
                    delay(500)
                    activity.getBinding().gameMessage.text=""
                }
            }
        }


    }
    private fun calculateDamage(attacker: Pokemon, target : Pokemon,
                                move: Move, attack :Int, defense: Int) : Int {
        val damageChart = DamageChart()
        // Calculates multiplier for one pokemon type
        val check = checkMatchingType(move, target.getTypes())
        val similarTypeMultiplier = 1.5
        val baseDamage = (((1.0/50.0)* (((2.0*attacker.getLevel().toDouble())/5.0)+2.0) *
                            move.getPower().toDouble()*((attack.toDouble()/defense.toDouble())+2.0)))
        var effectiveMultiplier = damageChart.getDamageMultiplier(move.getTypes(), target.getTypes()[0])
        // Check if target has many types
        if (target.getTypes().size == 2){
            effectiveMultiplier *= damageChart.getDamageMultiplier(move.getTypes(), target.getTypes()[1])
        }
        // If move type is similar to target type, there will be an additional multiplier
        if(check){
            return (baseDamage * similarTypeMultiplier * effectiveMultiplier).toInt()
        }
        return (baseDamage * effectiveMultiplier).toInt()
    }
    // Checks if attacker's move matches target's type
    private fun checkMatchingType(move: Move, types: ArrayList<String>): Boolean {
        for(type in types){
            if(type == move.getTypes()){
                return true
            }
        }
        return false
    }
    // Checks if ally full team is dead
    public fun isAllyTeamDead():Boolean{
        return isTeamDead(allyPokemonTeam)
    }
    // Check if pokemon team dead
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
    public fun enemyAttack(){
        activity.lifecycleScope.launch(Dispatchers.Default) {
            delay(1000)
            withContext(Dispatchers.Main) {
                var enemyMove = pickEnemyRandomMove()
                // Attacks swapped Pokemon
                attackPokemonTarget(currentAllyPokemon,currentEnemyPokemon, enemyMove.move)
                activity.getBinding().gameMessage.text =
                    "${currentEnemyPokemon.getName()} used ${enemyMove.moveName}!"
                delay(1000)
                activity.getBinding().gameMessage.text = ""
                delay(1500)
                // Updates pokemon text
                activity.getBinding().allyPokemonHp.text =
                    "${currentAllyPokemon.getCurrentHp()}/${currentAllyPokemon.getMaxHp()} HP"
            }
        }
    }
    // Add experience to pokemon when fight is won
    public fun addExperience(){
        Log.d("exp", currentAllyPokemon.getExperience().toString())
        val expGain = 0.3 * getCurrentEnemyPokemon().getExperience().toDouble() * getCurrentEnemyPokemon().getLevel().toDouble()
        currentAllyPokemon.addExperience(expGain)
        Log.d("exp", expGain.toString())
        Log.d("exp", currentAllyPokemon.getExperience().toString())
    }
}

