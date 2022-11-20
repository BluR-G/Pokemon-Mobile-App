package com.example.pokemon.fight

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam

class FightPokemonTeamAdapter (private val pokemonTeam: PokemonTeam, private val context: Context) : RecyclerView.Adapter<FightPokemonTeamAdapter.TeamViewHolder>() {
    //Define viewholder for managing roll textViews
    lateinit var fightActivity: FightActivity
    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonButton: Button = view.findViewById<Button>(R.id.pokemon_text_item)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        fightActivity = context as FightActivity
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pokemon_item, parent, false)

        return TeamViewHolder(layout)

    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val pokemon = pokemonTeam.getPokemonTeam()[position]
        val name = pokemon.getName()
        val level = pokemon.getLevel()
        val currentHp = pokemon.getCurrentHp()
        val maxHp = pokemon.getMaxHp()
        holder.pokemonButton.text = "${name} Lv.${level} HP:${currentHp}/${maxHp}"
        holder.pokemonButton.setOnClickListener{ view: View ->
            handleEvent(view, pokemon)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun handleEvent(
        view: View,
        pokemon: Pokemon
    ) {
        val previousFragmentId = view.findNavController().previousBackStackEntry?.destination?.displayName
        if (previousFragmentId == context.getString(R.string.teamFightFragment)) {
            healPokemon(view, pokemon)
        } else if (previousFragmentId == context.getString(R.string.potionFragment)) {
            swapPokemon(view, pokemon)
        }
    }

    private fun healPokemon(view : View, pokemon: Pokemon) {
        val name = pokemon.getName()
        Log.d("fragmentcheck", "heal $name")
        Log.d("heal", "before: $name ${pokemon.getCurrentHp()}")
        pokemon.setCurrentHp(pokemon.getCurrentHp() + 20)
        notifyDataSetChanged()
        Log.d("heal", "after: $name ${pokemon.getCurrentHp()}")
        view.findNavController().navigate(R.id.action_fightPokemonTeamFragment_to_fightMenuFragment)
    }
    private fun swapPokemon(view: View, pokemon: Pokemon){
        Log.d("testing", "reached")
        if(fightActivity.getCurrentPokemon() != pokemon){
            fightActivity.setCurrentPokemon(pokemon)
            fightActivity.getBinding().pokemonFightText.text = pokemon.getName()
            view.findNavController().navigate(R.id.action_fightPokemonTeamFragment_to_fightMenuFragment)
        } else {
            Log.d("swap", "cannot swap")
        }
    }

    override fun getItemCount(): Int {
        return pokemonTeam.getPokemonTeam().size
    }

}