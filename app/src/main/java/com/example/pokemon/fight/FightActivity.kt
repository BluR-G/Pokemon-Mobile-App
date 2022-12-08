package com.example.pokemon.fight

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pokemon.data.PokemonCreation
import com.example.pokemon.databinding.ActivityFightBinding
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonCollection
import com.example.pokemon.objects.PokemonTeam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class FightActivity : AppCompatActivity(){
    private lateinit var binding: ActivityFightBinding
    private lateinit var currentPokemon : Pokemon
    private lateinit var enemy : Pokemon
    private lateinit var pokemonTeam: PokemonTeam
    private lateinit var pokemonCollection: PokemonCollection
    private lateinit var battle: Battle
    private lateinit var battleType: String
    private lateinit var activity: FightActivity
    // 0 for ongoing fight, -1 for finished fight
    private var fightState = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFightBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this
        this.pokemonTeam = intent.getSerializableExtra("pokemonTeam") as PokemonTeam
        this.pokemonCollection = intent.getSerializableExtra("pokemonCollection") as PokemonCollection
        this.battleType=intent.getStringExtra("battleType") as String
        if(battleType == "wild"){
            lifecycleScope.launch(Dispatchers.IO) {
                // Generate wild pokemon
                enemy = generatePokemon()
                lifecycleScope.launch(Dispatchers.Main) {
                    activity.battle = WildBattle(pokemonTeam, enemy, activity)
                }
            }

        } else if(battleType == "trainer"){
            lifecycleScope.launch(Dispatchers.IO) {
                // Generate trainer pokemon team
                val enemyTeam = generatePokemonTeam()
                enemy = enemyTeam.getPokemon(0)
                lifecycleScope.launch(Dispatchers.Main) {
                    activity.battle = TrainerBattle(pokemonTeam, enemyTeam, activity)
                }
            }
        }
        currentPokemon = this.pokemonTeam.getPokemonTeam()[getCurrentPokemonIndex()]
       // binding.allyPokemonSprite.setImageBitmap(getImage(currentPokemon,1))
        binding.allyPokemon.text=currentPokemon.getName()
        binding.allyPokemonHp.text="HP: ${currentPokemon.getCurrentHp()}/${currentPokemon.getMaxHp()}"
        binding.allyLevel.text = "lv.${currentPokemon.getLevel()}"

    }
    public fun getFightState(): Int{
        return this.fightState
    }
    public fun setFightState(state: Int){
        this.fightState = state
    }
    public fun getBinding() : ActivityFightBinding{
        return this.binding;
    }
    public fun getCurrentPokemon() : Pokemon{
        return this.currentPokemon;
    }
    public fun setCurrentPokemon(pokemon : Pokemon){
        this.currentPokemon = pokemon;
    }
    public fun getPokemonTeam() : PokemonTeam {
        return this.pokemonTeam
    }
    public fun getPokemonCollection(): PokemonCollection{
        return this.pokemonCollection
    }
    public fun getBattle():Battle{
        return this.battle
    }
    public fun getImage(pokemon: Pokemon, id: Int): Bitmap {
        val img = pokemon.getImages()
        val imgFront = img[id]
        val imageBytes = Base64.decode(imgFront, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
    // Generate Random Wild Pokemon
    private suspend fun generatePokemon() : Pokemon {
        val creator = PokemonCreation()
        val pokemonId = (1..151).random().toString()
        val level = if(getLowestLevel()>5){
            (getLowestLevel()-5..getHighestLevel()+5).random()
        } else {
            (getLowestLevel()..getHighestLevel()+5).random()
        }

        return creator.createPokemon(pokemonId, "", level)
    }
    // Generate random pokemon team based on team level
    private suspend fun generatePokemonTeam(): PokemonTeam{
        val pokemonTeam = PokemonTeam()
        val pokemonCount = (0..5).random()
        for(i in 0 .. pokemonCount){
            pokemonTeam.addPokemonToTeam(generatePokemon())
        }
        return pokemonTeam
    }
    // Return index of first pokemon alive
    private fun getCurrentPokemonIndex(): Int {
        var index = 0
        for(pokemon in pokemonTeam.getPokemonTeam()){
            if(pokemon.isAlive()){
                return index
            } else {
                index++
            }
        }
        return index
    }
    private fun getHighestLevel(): Int{
        var highLevel = pokemonTeam.getPokemonTeam()[0].getLevel()
        for(pokemon in pokemonTeam.getPokemonTeam()){
            if(pokemon.getLevel() > highLevel){
                highLevel = pokemon.getLevel()
            }
        }
        return highLevel
    }
    private fun getLowestLevel():Int{
        var lowLevel = pokemonTeam.getPokemonTeam()[0].getLevel()
        for(pokemon in pokemonTeam.getPokemonTeam()){
            if(pokemon.getLevel() < lowLevel){
                lowLevel = pokemon.getLevel()
            }
        }
        return lowLevel
    }

}