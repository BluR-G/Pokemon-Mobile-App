package com.example.pokemon.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.objects.PokemonTeam

class PokemonTeamMenuAdaptor(team: PokemonTeam) : RecyclerView.Adapter<PokemonTeamMenuAdaptor.PokemonViewHolder>(){
    private var pokemonTeam = team

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val pokemonName: TextView = view.findViewById(R.id.pokemon_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pokemon_team_menu_adaptor, parent, false)
        return PokemonViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.pokemonName.text = pokemonTeam.getPokemon(position).getName()
    }

    override fun getItemCount(): Int = pokemonTeam.getSize()
}