package com.example.pokemon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.objects.PokemonTeam

class FightPokemonTeamAdapter (private val pokemonTeam: PokemonTeam, private val context: Context) : RecyclerView.Adapter<FightPokemonTeamAdapter.TeamViewHolder>() {
    //Define viewholder for managing roll textViews
    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonButton: Button = view.findViewById<Button>(R.id.pokemon_text_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pokemon_item, parent, false)

        return TeamViewHolder(layout)

    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val item = pokemonTeam.getPokemonTeam()[position]
        val name = item.getName()
        holder.pokemonButton.text = name
    }

    override fun getItemCount(): Int {
        return pokemonTeam.getPokemonTeam().size
    }

}