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
    private lateinit var fightActivity: FightActivity
    private lateinit var battle: Battle
    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonButton: Button = view.findViewById<Button>(R.id.pokemon_text_item)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        fightActivity = context as FightActivity
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pokemon_item, parent, false)
        battle = fightActivity.getBattle()
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
        holder.pokemonButton.isClickable
    }

    @SuppressLint("RestrictedApi")
    private fun handleEvent(
        view: View,
        pokemon: Pokemon
    ) {
        val previousFragmentId = view.findNavController().previousBackStackEntry?.destination?.displayName
        if (previousFragmentId == context.getString(R.string.teamFightFragment)) {
            battle.usePotion(view, pokemon)
        } else if (previousFragmentId == context.getString(R.string.potionFragment)) {
            battle.swapPokemon(view, pokemon)
        }
    }

    override fun getItemCount(): Int {
        return pokemonTeam.getPokemonTeam().size
    }

}