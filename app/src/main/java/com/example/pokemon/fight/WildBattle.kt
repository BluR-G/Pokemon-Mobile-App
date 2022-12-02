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

class WildBattle(pokemonTeam: PokemonTeam, activity: FightActivity) : Battle(pokemonTeam, activity){
    init {
        generatePokemon()
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
                displayFinalMessage("You won")
                // TODO: Apply XP to Pokemon
            }
        } else {
            if(isAlive(pokemonAttacker)){
                attackPokemonTarget(pokemonTarget, attackerMove.move, view)
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
            displayFinalMessage("You won")
        } else if(isAllyTeamDead()){
            displayFinalMessage("You lost!")
        }
    }
    // Catch Pokemon
    override fun throwPokeball(view: View) {
        view.findNavController().navigate(R.id.action_bagFragment_to_fightMenuFragment)
        // TODO: Pokemon catching mechanics
    }
    // Generate Random Wild Pokemon
    fun generatePokemon(){
        val creator = PokemonCreation()
        activity.lifecycleScope.launch(Dispatchers.IO) {
            var pokemon = creator.createPokemon("bulbasaur", "bulb", 10)
            setCurrentEnemyPokemon(pokemon)
        }
    }
    private fun addMoves(moves: ArrayList<MoveData>){
//        val types = arrayOf("fire")
//        val move1 = Move(100,40,"other","special", "", 40, types)
//        val move2 = Move(100,70,"other","special", "", 40, types)
//        val move3 = Move(100,10,"other","special", "", 40, types)
//        val move4 = Move(100,35,"other","special", "", 40, types)
//        moves.add(MoveData("ember", 5,move1))
//        moves.add(MoveData("whip", 5,move2))
//        moves.add(MoveData("tackle", 5,move3))
//        moves.add(MoveData("sleep", 5,move4))
    }
}