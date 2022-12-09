package com.example.pokemon.fight

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.pokemon.R
import com.example.pokemon.menu.MenuActivity
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
    public var newMove: MoveData? = null
    public var newNickname=""
    public var capturedSpecies=""
    public lateinit var activity: FightActivity

    constructor(pokemonTeam: PokemonTeam, currentEnemyPokemon: Pokemon,activity: FightActivity) {
        this.activity = activity
        this.allyPokemonTeam = pokemonTeam
        this.allyPokemonCollection = activity.getPokemonCollection()
        this.currentAllyPokemon = pokemonTeam.getPokemonTeam()[0]
        this.currentEnemyPokemon = currentEnemyPokemon
    }

    abstract fun checkPokemonStatus(pokemonTarget: Pokemon, pokemonAttacker: Pokemon, attackerMove : MoveData, view : View)

    abstract fun fight(view: View, allyMoveData : MoveData)

    abstract fun throwPokeball(view: View)

    fun initializeMessage(message: String) {
            activity.getBinding().enemyPokemonFront.setImageBitmap(getImage(getCurrentEnemyPokemon(), 0))
            activity.getBinding().enemyPokemonText.text = getCurrentEnemyPokemon().getSpecies()
            activity.getBinding().enemyPokemonHp.text =
                "HP:${getCurrentEnemyPokemon().getCurrentHp()}/${getCurrentEnemyPokemon().getMaxHp()}"
            activity.getBinding().enemyLevel.text = "lv.${getCurrentEnemyPokemon().getLevel()}"
            activity.lifecycleScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    delay(200)
                    activity.getBinding().gameMessage.text = message
                    delay(1500)
                    activity.getBinding().gameMessage.text = ""
                }
            }
    }
    fun getImage(pokemon: Pokemon, id: Int): Bitmap {
        val img = pokemon.getImages()
        val imgFront = img[id]
        val imageBytes = Base64.decode(imgFront, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
    fun getCurrentEnemyPokemon(): Pokemon{
        return this.currentEnemyPokemon
    }

    fun setCurrentEnemyPokemon(pokemon: Pokemon){
        this.currentEnemyPokemon = pokemon
    }

    // Plays the turns of both pokemon
    fun playPokemonsTurns(move:MoveData, enemyMove: MoveData, view: View){
        activity.setFightState(0)
        activity.lifecycleScope.launch(Dispatchers.Main){
                if(currentAllyPokemon.getSpeed() > getCurrentEnemyPokemon().getSpeed()){
                    checkPokemonStatus(getCurrentEnemyPokemon(), currentAllyPokemon, move, view)
                    delay(1500)
                    checkPokemonStatus(currentAllyPokemon, getCurrentEnemyPokemon(), enemyMove, view)
                } else {
                    checkPokemonStatus(currentAllyPokemon, getCurrentEnemyPokemon(),  enemyMove, view)
                    delay(1500)
                    checkPokemonStatus(getCurrentEnemyPokemon(),currentAllyPokemon, move, view)
                }
        }
    }

    // Swap pokemon
    fun swapPokemon(view: View, pokemon: Pokemon){
        lateinit var enemyMove : MoveData
        if(pokemon.isAlive()){
            if(activity.getCurrentPokemon() != pokemon){
                currentAllyPokemon = pokemon
                currentAllyPokemon = pokemon
                activity.setCurrentPokemon(pokemon)
                enemyMove = pickEnemyRandomMove()
                updateSwapMessage(pokemon, currentEnemyPokemon,enemyMove, view)
            } else {
                displayCannotSwapMessage()
            }
        }
    }

    // Attack pokemon target with move
    fun attackPokemonTarget(attacker: Pokemon, target: Pokemon, move: Move){
        val moveChance = (1..100).random()
        var moveDamage = 0
        if(move.getDamageClass() == "PHYSICAL"){
            moveDamage = calculateDamage(attacker, target, move, attacker.getAttack(),
                                         target.getDefense())
        } else if(move.getDamageClass() == "SPECIAL") {
            moveDamage = calculateDamage(attacker, target, move, attacker.getSpecialAttack(),
                                         target.getSpecialDefense())
        }

        if(move.getAccuracy()>=moveChance){
            val targetHp = target.getCurrentHp() - moveDamage
            val selfHealHp = attacker.getCurrentHp() + move.getHeal()
            if(move.getHeal()>0){
                selfHealMove(selfHealHp, attacker)
            }
            if (targetHp < 0){
                handleCurrentPokemonDeath(target)
            } else {
                target.setCurrentHp(targetHp)
            }
        } else {
            activity.lifecycleScope.launch(Dispatchers.Default){
                withContext(Dispatchers.Main){
                    activity.getBinding().gameMessage.text="${attacker.getName()} missed!"
                    delay(1000)
                    activity.getBinding().gameMessage.text=""
                }
            }
        }
    }
    // Attacks target pokemon
    private fun handleCurrentPokemonDeath(target: Pokemon) {
        target.setCurrentHp(0)
        if (target == currentAllyPokemon) {
            val navHostFragment =
                activity.supportFragmentManager.findFragmentById(R.id.fightNavHostFragment) as NavHostFragment
            val navController = navHostFragment.navController
            // If team is still alive, give the choice to swap pokemon
            if (!allyPokemonTeam.isTeamDead()) {
                activity.lifecycleScope.launch(Dispatchers.Main){
                    delay(1000)
                    activity.getBinding().gameMessage.text="${currentAllyPokemon.getName()} fainted. Pick another Pokemon!"
                    delay(2000)
                    activity.getBinding().gameMessage.text=""
                }
                navController.navigate(R.id.action_fightMenuFragment_to_fightPokemonTeamFragment)
            }
        }
    }
    // Self heal if move provides heal
    private fun selfHealMove(selfHealHp: Int, attacker: Pokemon) {
        if (selfHealHp > attacker.getCurrentHp()) {
            attacker.setCurrentHp(attacker.getMaxHp())
        } else {
            attacker.setCurrentHp(selfHealHp)
        }
        activity.lifecycleScope.launch(Dispatchers.Main) {
            if (attacker == currentAllyPokemon) {
                activity.getBinding().allyPokemonHp.text =
                    "HP:${attacker.getCurrentHp()}/${attacker.getMaxHp()}"
            } else {
                activity.getBinding().enemyPokemonHp.text =
                    "HP:${attacker.getCurrentHp()}/${attacker.getMaxHp()}"
            }
        }
    }

    // Calculate base damage with multipliers according to pokemon types
    private fun calculateDamage(attacker: Pokemon, target : Pokemon, move: Move,
                                attack :Int, defense: Int) : Int {
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
    // Make enemy instantly attack when ally round is skipped
    fun enemyAttack(){
        activity.lifecycleScope.launch(Dispatchers.Default) {
            delay(1000)
            withContext(Dispatchers.Main) {
                var enemyMove = pickEnemyRandomMove()
                // Attacks swapped Pokemon
                attackPokemonTarget(currentEnemyPokemon,currentAllyPokemon, enemyMove.move)
                activity.getBinding().gameMessage.text =
                    "${currentEnemyPokemon.getName()} used ${enemyMove.moveName}!"
                delay(1000)
                activity.getBinding().gameMessage.text = ""
                delay(1000)
                // Updates pokemon text
                activity.getBinding().allyPokemonHp.text =
                    "HP:${currentAllyPokemon.getCurrentHp()}/${currentAllyPokemon.getMaxHp()}"
            }
        }
    }
    // Teach moves to pokemon if it levels up
    suspend fun checkAddToCurrentMoves(previousLevel: Int) {
        if (previousLevel < currentAllyPokemon.getLevel()) {
            activity.getBinding().allyLevel.text="lv.${currentAllyPokemon.getLevel()}"
            //Level up
            val movesList =
                currentAllyPokemon.checkAcquiredMoves(previousLevel, currentAllyPokemon.getLevel())
            if (movesList.size > 0) {
                for (move in movesList) {
                    activity.setFightState(-1)
                    if (currentAllyPokemon.getCurrentMoves().size == 4) {
                        // Replace move
                        newMove = move
                        delay(2000)
                        activity.getBinding().gameMessage.text =
                            "${currentAllyPokemon.getName()} wants to learn ${move.moveName}. Pick one move to replace"
                        val navHostFragment =
                            activity.supportFragmentManager.findFragmentById(R.id.fightNavHostFragment) as NavHostFragment
                        val navController = navHostFragment.navController
                        if (navController.currentDestination.toString() == activity.getString(R.string.fight_menu_navigation)) {
                            // Navigate to moves fragment so that user picks which move to replace
                            navController.navigate(R.id.action_fightMenuFragment_to_fightFragment)
                        }
                    } else {
                        // add move
                        currentAllyPokemon.addCurrentMove(move)
                    }
                }
            }

        }
        activity.setFightState(0)
    }
    // Replace selected move with new move
    fun replaceMove(pokemon: Pokemon, movePosition: Int){
        if(newMove != null){
            val move = newMove!!.copy()
            currentAllyPokemon.getCurrentMoves()[movePosition]=move
            // Activate state to replace moves instead of fighting
            activity.lifecycleScope.launch(Dispatchers.Main){
                activity.getBinding().gameMessage.text="${pokemon.getName()} learned ${move.moveName}!"
                delay(1500)
                activity.getBinding().gameMessage.text=""
                activity.setFightState(0)
            }

        }

    }
    // Generates the next enemy move
    fun pickEnemyRandomMove(): MoveData {
        val random = Random()
        val enemyMoves = currentEnemyPokemon.getCurrentMoves()
        val moveNumber = random.nextInt(enemyMoves.size)
        return enemyMoves[moveNumber]
    }
    // Apply potion effect to pokemon
    fun usePotion(view: View, pokemon: Pokemon){
        heal(pokemon)
        updateHealMessage(view, pokemon)
    }
    // Heal Pokemon
    private fun heal(pokemon: Pokemon){
        val potionHeal = 20
        if(pokemon.isAlive()){
            var healHp = pokemon.getCurrentHp()+ potionHeal
            if(healHp<pokemon.getMaxHp()){
                pokemon.setCurrentHp(healHp)
            } else {
                pokemon.setCurrentHp(pokemon.getMaxHp())
            }
        }
    }

    // Exits the current activity and sends back team to menu
    fun run(){
        val intent = Intent(activity, MenuActivity::class.java)
        intent.putExtra("pokemonTeam", activity.getPokemonTeam())
        intent.putExtra("pokemonCollection", activity.getPokemonCollection())
        activity.setResult(Activity.RESULT_OK, intent)
        activity.finish()
    }

    // Display the win or loss message and exits fight
    fun displayFinalMessage(message:String){
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text=message
                delay(1500)
                run()
            }
        }
    }

    // Warns player that they cannot swap to same pokemon
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
    fun updateFightMessage(attacker: Pokemon, target:  Pokemon, firstMove: MoveData){
        activity.lifecycleScope.launch(Dispatchers.Main){
            activity.getBinding().gameMessage.text="${attacker.getName()} used ${firstMove.moveName}!"
            delay(500)
            // Set HP status to appropriate pokemon target
            if(attacker == currentAllyPokemon){
                activity.getBinding().enemyPokemonHp.text="HP:${target.getCurrentHp()}/${target.getMaxHp()}"
            } else if(attacker == currentEnemyPokemon){
                activity.getBinding().allyPokemonHp.text="HP:${target.getCurrentHp()}/${target.getMaxHp()}"
            }
            activity.getBinding().gameMessage.text=""
        }
    }

    // Updates game message when current pokemon is swapped
    fun updateSwapMessage(pokemon: Pokemon, enemyPokemon: Pokemon, enemyMove: MoveData, view: View){
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text="Swapping to ${pokemon.getName()} !"
                view.findNavController().navigate(R.id.action_fightPokemonTeamFragment_to_fightMenuFragment)
                delay(1000)
                activity.getBinding().allyPokemonBack.setImageBitmap(activity.getImage(currentAllyPokemon,1))
                activity.getBinding().allyPokemon.text=pokemon.getName()
                activity.getBinding().allyPokemonHp.text="HP:${currentAllyPokemon.getCurrentHp()}/${currentAllyPokemon.getMaxHp()}"
                activity.getBinding().allyLevel.text = "lv.${currentAllyPokemon.getLevel()}"
            }
            delay(1000)
            // Update information of swapped pokemon after getting attacked
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text=""
                activity.getBinding().gameMessage.text="${enemyPokemon.getName()} used ${enemyMove.moveName}!"
                delay(1500)
                attackPokemonTarget(currentEnemyPokemon,currentAllyPokemon, enemyMove.move)
                activity.getBinding().allyPokemonHp.text="HP:${currentAllyPokemon.getCurrentHp()}/${currentAllyPokemon.getMaxHp()}"
                activity.getBinding().gameMessage.text=""
            }
        }
    }

    // Updates game message when current pokemon is healed
    fun updateHealMessage(view: View, pokemon: Pokemon){
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                view.findNavController().navigate(R.id.action_fightPokemonTeamFragment_to_fightMenuFragment)
                // Show current hp
                activity.getBinding().gameMessage.text="${pokemon.getName()} has now ${pokemon.getCurrentHp()} HP!"
                activity.getBinding().allyPokemonHp.text="HP:${currentAllyPokemon.getCurrentHp()}/${currentAllyPokemon.getMaxHp()}"
                delay(1000)
                activity.getBinding().gameMessage.text=""
            }
            // Ally pokemon skips a round and enemy attacks
            enemyAttack()
        }
    }

    // Add experience to pokemon when fight is won
    public fun addExperience(){
        val expGain = 0.3 * getCurrentEnemyPokemon().getExperienceReward() * getCurrentEnemyPokemon().getLevel() + 1000
        currentAllyPokemon.addExperience(expGain.toInt())
    }

}

