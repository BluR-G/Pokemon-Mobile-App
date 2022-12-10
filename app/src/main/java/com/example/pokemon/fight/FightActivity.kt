package com.example.pokemon.fight

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pokemon.databinding.ActivityFightBinding
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonCollection
import com.example.pokemon.objects.PokemonTeam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FightActivity : AppCompatActivity(){
    private lateinit var binding: ActivityFightBinding
    private lateinit var currentPokemon : Pokemon
    private lateinit var enemy : Pokemon
    private lateinit var pokemonTeam: PokemonTeam
    private lateinit var pokemonCollection: PokemonCollection
    private lateinit var battle: Battle
    private lateinit var battleType: String
    private lateinit var activity: FightActivity
    // 0 for ongoing fight, -1 for paused fight,
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
                // Generate wild pokemon
                enemy = intent.getSerializableExtra("wildPokemon") as Pokemon
                lifecycleScope.launch(Dispatchers.Main) {
                    activity.battle = WildBattle(pokemonTeam, enemy, activity)
                }
        } else if(battleType == "trainer"){
                val enemyTeam = intent.getSerializableExtra("trainerTeam") as PokemonTeam
                lifecycleScope.launch(Dispatchers.Main) {
                    activity.battle = TrainerBattle(pokemonTeam, enemyTeam, activity)
                }
        }
        currentPokemon = this.pokemonTeam.getPokemonTeam()[getCurrentPokemonIndex()]

        binding.allyPokemonBack.setImageBitmap(getImage(currentPokemon,1))
        binding.allyPokemon.text=currentPokemon.getName()
        binding.allyPokemonHp.text="HP:${currentPokemon.getCurrentHp()}/${currentPokemon.getMaxHp()}"
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
    public fun getEnemyPokemon() : Pokemon {
        return this.enemy
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

    override fun onBackPressed() {
        Toast.makeText(this, "Button Disabled", Toast.LENGTH_SHORT).show()
    }
}