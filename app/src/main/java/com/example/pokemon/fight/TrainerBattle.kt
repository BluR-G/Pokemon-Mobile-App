package com.example.pokemon.fight

import android.graphics.drawable.Drawable
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

class TrainerBattle(pokemonTeam: PokemonTeam, activity: FightActivity) : Battle(pokemonTeam, activity) {
    private lateinit var enemyTeam: PokemonTeam
    private var count = 0

    init {
        this.enemyTeam = generateEnemyTeam()
        setCurrentEnemyPokemon(enemyTeam.getPokemonTeam()[0])
        initializeMessage()
    }
    private fun initializeMessage(){
        activity.getBinding().enemyPokemonText.text=getCurrentEnemyPokemon().getSpecies()
        activity.getBinding().enemyPokemonHp.text="HP: ${getCurrentEnemyPokemon().getCurrentHp()}/${getCurrentEnemyPokemon().getMaxHp()}"
        activity.getBinding().enemyLevel.text = "lv.${getCurrentEnemyPokemon().getLevel()}"
    }
    public override fun checkPokemonStatus(pokemonTarget: Pokemon, pokemonAttacker: Pokemon, attackerMove : MoveData, view : View){
        if(!isAlive(pokemonTarget)){
            if(pokemonTarget == getCurrentEnemyPokemon()){
                val check = swapEnemy()
            }
        } else {
            if(isAlive(pokemonAttacker)){
                attackPokemonTarget(pokemonTarget, attackerMove.move, view)
                updateFightMessage(pokemonTarget,pokemonAttacker,attackerMove)
            }
        }
    }
    // Fight between the current pokemon
    public override fun fight(view: View, allyMoveData : MoveData){
        view.findNavController().navigate(R.id.action_fightFragment_to_fightMenuFragment)
        val enemyMove = pickEnemyRandomMove()
        val move = allyMoveData
        checkPokemonStatus(getCurrentEnemyPokemon(), currentAllyPokemon, move, view)
        checkPokemonStatus(currentAllyPokemon, getCurrentEnemyPokemon(), enemyMove, view)
        if(!isAlive(getCurrentEnemyPokemon())){
            swapEnemy()
        }
        if(isEnemyTeamDead()){
          displayFinalMessage("You won")
        } else if(isAllyTeamDead()){
            displayFinalMessage("You lost!")
        }
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
    private fun swapEnemy(): Boolean{
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
            return true
        }
        return false
    }

    // Checks if enemy full team is dead
    private fun isEnemyTeamDead():Boolean{
        return isTeamDead(enemyTeam)
    }

    // Sample data methods
    private fun addMoves(moves: ArrayList<MoveData>){
        val types = ArrayList<String>()
        val move1 = Move(100,40,"other",0, "", "sleep",0,"grass")
        val move2 = Move(100,70,"other",0, "", "sleep",0,"grass")
        val move3 = Move(100,10,"other",0, "", "sleep",0,"grass")
        val move4 = Move(100,35,"other",0, "", "sleep",0,"grass")
        moves.add(MoveData("ember", 5,move1))
        moves.add(MoveData("whip", 5,move2))
        moves.add(MoveData("tackle", 5,move3))
        moves.add(MoveData("sleep", 5,move4))
    }
    private fun generateEnemyTeam() : PokemonTeam {
        // generate enemy team according to average lvl
        var enemyTeam = PokemonTeam()
        var types = ArrayList<String>()
        types.add("water")
        val moves = ArrayList<MoveData>()
        addMoves(moves)
        var images = ArrayList<String>()
        enemyTeam.addPokemonToTeam(
            Pokemon("blastoise","blastoise",36,types,
                20,30,40,100,200,100, moves, images)
        )
        enemyTeam.addPokemonToTeam(
            Pokemon("bulbausar","bulbausar",17,types,
                60,30,40,100,200,100, moves, images)
        )
//        enemyTeam.addPokemonToTeam(
//            Pokemon("pidgeotto","pidgeotto",36,types,
//                60,30,40,100,200,100, moves, images)
//        )
//        enemyTeam.addPokemonToTeam(
//            Pokemon("dragonite","dragonite",36,types,
//                60,30,40,100,200,100, moves, images)
//        )
//        enemyTeam.addPokemonToTeam(
//            Pokemon("machamp","machamp",36,types,
//                60,30,40,100,200,100, moves, images)
//        )
//        enemyTeam.addPokemonToTeam(
//            Pokemon("pidgeon","pidgeon",36,types,
//                60,30,40,100,200,100, moves, images)
//        )
        return enemyTeam
    }
}