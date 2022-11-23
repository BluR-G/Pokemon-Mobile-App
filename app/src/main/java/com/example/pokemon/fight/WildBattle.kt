package com.example.pokemon.fight

import android.graphics.drawable.Drawable
import android.view.View
import androidx.navigation.findNavController
import com.example.pokemon.R
import com.example.pokemon.objects.Move
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam

class WildBattle(pokemonTeam: PokemonTeam, activity: FightActivity) : Battle(pokemonTeam, activity){
    private lateinit var allyPokemonTeam: PokemonTeam
    private lateinit var currentAllyPokemon : Pokemon
    private lateinit var activity: FightActivity
    init {
        this.activity = activity
        this.allyPokemonTeam = pokemonTeam
        setCurrentEnemyPokemon(generatePokemon())
        this.currentAllyPokemon = pokemonTeam.getPokemonTeam()[0]
        activity.getBinding().enemyPokemonText.text=getCurrentEnemyPokemon().getName()
        activity.getBinding().enemyPokemonHp.text="HP: ${getCurrentEnemyPokemon().getCurrentHp()}/${getCurrentEnemyPokemon().getMaxHp()}"
    }
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
                updateMessage(pokemonTarget,pokemonAttacker,attackerMove)
            }
        }
    }

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

    override fun throwPokeball(view: View) {
        view.findNavController().navigate(R.id.action_bagFragment_to_fightMenuFragment)
    }

    fun generatePokemon():Pokemon{
        val types = arrayOf<String>("grass")
        val moves = ArrayList<MoveData>()
        addMoves(moves)
        val images = arrayOf<Drawable>()
        return Pokemon("bulbausar","bulbausar",36,types,
            60,30,40,100,200,100, moves, images)
    }
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
}