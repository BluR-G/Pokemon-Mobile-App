package com.example.pokemon.fight

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
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
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class Game() {
    private lateinit var allyPokemonTeam: PokemonTeam
    private lateinit var enemyTeam: PokemonTeam
    private lateinit var currentAllyPokemon : Pokemon
    private lateinit var currentEnemyPokemon: Pokemon
    private lateinit var activity: FightActivity

    constructor(pokemonTeam: PokemonTeam, activity: FightActivity) : this() {
        this.activity = activity
        this.allyPokemonTeam = pokemonTeam
        this.enemyTeam = generateEnemyTeam()
        this.currentAllyPokemon = pokemonTeam.getPokemonTeam()[0]
        this.currentEnemyPokemon = enemyTeam.getPokemonTeam()[0]
        activity.getBinding().enemyPokemonText.text=currentEnemyPokemon.getName()
        activity.getBinding().enemyPokemonHp.text="HP: ${currentEnemyPokemon.getCurrentHp()}/${currentEnemyPokemon.getMaxHp()}"
    }
    // Fight between the current pokemon
    public fun fight(view: View, allyMoveData : MoveData){
        val enemyMove = pickEnemyRandomMove()
        val move = allyMoveData.move
        // Ally Fight
        view.findNavController().navigate(R.id.action_fightFragment_to_fightMenuFragment)
        // Enemy Fight
        attackPokemonTarget(currentEnemyPokemon, move, view)
        attackPokemonTarget(currentAllyPokemon, enemyMove.move,view)
        updateFightMessage(currentEnemyPokemon,currentAllyPokemon,allyMoveData,enemyMove)
        if(!isAlive(currentEnemyPokemon)){
            // TODO gain exp
            // TODO swap pokemon
        }
        if(isAllyTeamDead()){
            Log.d("fight", "You lost.")
            // TODO lose message, go straight to pokecenter
            run()
        } else if(isEnemyTeamDead()){
            Log.d("fight", "You won.")
            // TODO gain xp
            // TODO win message
            run()
        }
    }
    // Swap pokemon
    public fun swapPokemon(view: View, pokemon: Pokemon){
        lateinit var enemyMove : MoveData
        if(isAlive(pokemon)){
            currentAllyPokemon = pokemon
            if(activity.getCurrentPokemon() != pokemon){
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
        // navigate back to menu
        updateHealMessage(view, pokemon)
    }
    // Attempt to catch wild pokemon
    public fun throwPokeball(view:View){
        view.findNavController().navigate(R.id.action_bagFragment_to_fightMenuFragment)
    }
    private fun updateFightMessage(firstAttacked: Pokemon, secondAttacked: Pokemon, firstMove : MoveData, secondMove: MoveData){
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text="${secondAttacked.getName()} used ${firstMove.moveName}!"
                delay(1500)
                activity.getBinding().enemyPokemonHp.text="HP:${firstAttacked.getCurrentHp()}/${firstAttacked.getMaxHp()}"
                activity.getBinding().gameMessage.text=""
            }
            delay(1000)
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text="${firstAttacked.getName()} used ${secondMove.moveName}!"
                delay(1500)
                activity.getBinding().allyPokemonHp.text="HP:${firstAttacked.getCurrentHp()}/${firstAttacked.getMaxHp()}"
                activity.getBinding().gameMessage.text=""
            }
        }
    }
    private fun updateSwapMessage(pokemon: Pokemon, enemyPokemon: Pokemon, enemyMove: MoveData, view:View){
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                activity.getBinding().gameMessage.text="Swapping to ${pokemon.getName()} !"
                view.findNavController().navigate(R.id.action_fightPokemonTeamFragment_to_fightMenuFragment)
                delay(1000)
                activity.getBinding().pokemonFightText.text=pokemon.getName()
                activity.getBinding().allyPokemonHp.text="HP:${currentAllyPokemon.getCurrentHp()}/${currentAllyPokemon.getMaxHp()} HP"
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
    private fun updateHealMessage(view:View, pokemon: Pokemon){
        activity.lifecycleScope.launch(Dispatchers.Default){
            withContext(Dispatchers.Main){
                view.findNavController().navigate(R.id.action_fightPokemonTeamFragment_to_fightMenuFragment)
                activity.getBinding().gameMessage.text="${pokemon.getName()} has now ${pokemon.getCurrentHp()} HP!"
                activity.getBinding().allyPokemonHp.text="${currentAllyPokemon.getCurrentHp()}/${currentAllyPokemon.getMaxHp()} HP"
                delay(1000)
                activity.getBinding().gameMessage.text=""
            }
            delay(1000)
            withContext(Dispatchers.Main){
                var enemyMove = pickEnemyRandomMove()
                attackPokemonTarget(currentAllyPokemon,enemyMove.move,view)
                activity.getBinding().gameMessage.text="${currentEnemyPokemon.getName()} used ${enemyMove.moveName}!"
                delay(1000)
                activity.getBinding().gameMessage.text=""
                delay(1500)
                activity.getBinding().allyPokemonHp.text="${currentAllyPokemon.getCurrentHp()}/${currentAllyPokemon.getMaxHp()} HP"
            }
        }
    }
    // Heal Pokemon
    private fun heal(pokemon: Pokemon){
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
    private fun pickEnemyRandomMove() : MoveData{
        val random = Random()
        val moveNumber = random.nextInt(4)
        val enemyMove = currentEnemyPokemon.getMoves()[moveNumber]
        return enemyMove
    }
    // Attack pokemon target with move
    private fun attackPokemonTarget(target: Pokemon, move: Move,view:View){
        //Thread.sleep(1500)
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
    //public fun getCurrentAllyPokemon(): Pokemon {}
    public fun setCurrentAllyPokemon(pokemon: Pokemon) {}
    //public fun getCurrentEnemyPokemon(): Pokemon {}
    public fun setCurrentEnemyPokemon(pokemon: Pokemon) {}
    public fun isAllyTeamAlive():Boolean{
        return true
    }
    // Checks if enemy full team is dead
    private fun isEnemyTeamDead():Boolean{
        return isTeamDead(enemyTeam)
    }
    // Checks if ally full team is dead
    private fun isAllyTeamDead():Boolean{
        return isTeamDead(allyPokemonTeam)
    }
    // Check if team is pokemon dead
    private fun isTeamDead(pokemonTeam: PokemonTeam):Boolean{
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
    private fun isAlive(pokemon: Pokemon):Boolean{
        if(pokemon.getCurrentHp()==0){
            Log.d("fight", "${pokemon.getName()} is dead")
            return false
        }
        return true
    }



    // Sample data methods
    private fun addMoves(moves: ArrayList<MoveData>){
        val types = arrayOf("fire")
        val move1 = Move(100,40,"other","special", "", 40, types)
        val move2 = Move(100,70,"other","special", "", 40, types)
        val move3 = Move(100,10,"other","special", "", 40, types)
        val move4 = Move(100,35,"other","special", "", 40, types)
        moves.add(MoveData("ember", 5,move1))
        moves.add(MoveData("whip", 5,move2))
        moves.add(MoveData("tackle", 5,move3))
        moves.add(MoveData("sleep", 5,move4))
    }
    private fun generateEnemyTeam() : PokemonTeam {
        // generate enemy team according to average lvl
        var enemyTeam = PokemonTeam()
        var types = arrayOf<String>("water")
        val moves = ArrayList<MoveData>()
        addMoves(moves)
        var images = arrayOf<Drawable>()
        enemyTeam.addPokemonToTeam(
            Pokemon("blastoise","blastoise",36,types,
                200,30,40,100,200,100, moves, images)
        )
        enemyTeam.addPokemonToTeam(
            Pokemon("bulbausar","bulbausar",36,types,
                60,30,40,100,200,100, moves, images)
        )
        enemyTeam.addPokemonToTeam(
            Pokemon("pidgeotto","pidgeotto",36,types,
                60,30,40,100,200,100, moves, images)
        )
        enemyTeam.addPokemonToTeam(
            Pokemon("dragonite","dragonite",36,types,
                60,30,40,100,200,100, moves, images)
        )
        enemyTeam.addPokemonToTeam(
            Pokemon("machamp","machamp",36,types,
                60,30,40,100,200,100, moves, images)
        )
        enemyTeam.addPokemonToTeam(
            Pokemon("pidgeon","pidgeon",36,types,
                60,30,40,100,200,100, moves, images)
        )
        return enemyTeam
    }
}