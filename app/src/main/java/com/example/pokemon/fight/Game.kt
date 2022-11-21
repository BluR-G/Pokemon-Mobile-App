package com.example.pokemon.fight

import android.graphics.drawable.Drawable
import android.util.Log
import com.example.pokemon.objects.Move
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam
import java.util.*
import kotlin.collections.ArrayList

class Game() {
    private lateinit var pokemonTeam: PokemonTeam
    private lateinit var enemyTeam: PokemonTeam
    private lateinit var currentAllyPokemon : Pokemon
    private lateinit var currentEnemyPokemon: Pokemon
    private lateinit var activity: FightActivity

    constructor(pokemonTeam: PokemonTeam, activity: FightActivity) : this() {
        this.activity = activity
        this.pokemonTeam = pokemonTeam
        this.enemyTeam = generateEnemyTeam()
        this.currentAllyPokemon = pokemonTeam.getPokemonTeam()[0]
        this.currentEnemyPokemon = enemyTeam.getPokemonTeam()[0]
    }

    public fun play(){
        //fight

    }
    public fun attack(allyMovedata : MoveData){
        val random = Random()
        val moveNumber = random.nextInt(4)
        val enemyMove = currentEnemyPokemon.getMoves()[moveNumber]
        val move = allyMovedata.move
        // Ally Fight
        Log.d("fight","Ally Pokemon:${currentAllyPokemon.getName()} HP: ${currentAllyPokemon.getCurrentHp()}" )
        Log.d("fight","Enemy Pokemon:${currentEnemyPokemon.getName()} HP: ${currentEnemyPokemon.getCurrentHp()}" )
        Log.d("fight", "${currentAllyPokemon.getName()} used ${allyMovedata.moveName}!")
        Thread.sleep(1500)
        // Enemy Fight
        attackPokemonTarget(currentAllyPokemon, currentEnemyPokemon, move)
        Log.d("fight","After, Enemy Pokemon:${currentEnemyPokemon.getName()} HP: ${currentEnemyPokemon.getCurrentHp()}" )
        Log.d("fight", "${currentEnemyPokemon.getName()} used ${enemyMove.moveName}!")
        attackPokemonTarget(currentEnemyPokemon, currentAllyPokemon, enemyMove.move)
        Log.d("fight","After, Ally Pokemon:${currentAllyPokemon.getName()} HP: ${currentAllyPokemon.getCurrentHp()}" )
    }
    private fun attackPokemonTarget(attacker: Pokemon, target: Pokemon, move: Move){
        target.setCurrentHp(target.getCurrentHp()-move.getPower())
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
    //public fun getCurrentAllyPokemon(): Pokemon {}
    public fun setCurrentAllyPokemon(pokemon: Pokemon) {}
    //public fun getCurrentEnemyPokemon(): Pokemon {}
    public fun setCurrentEnemyPokemon(pokemon: Pokemon) {}
    public fun isAllyTeamAlive():Boolean{
        return true
    }
    public fun isEnemyTeamAlive():Boolean{
        return true
    }
    public fun isTeamAlive(pokemonTeam: PokemonTeam):Boolean{
        return false
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