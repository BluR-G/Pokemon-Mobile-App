package com.example.pokemon.menu

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.objects.PokemonTeam

class ChangeTeamOrderAdapter(team: PokemonTeam) : RecyclerView.Adapter<ChangeTeamOrderAdapter.PokemonViewHolder>() {
    private var pokemonTeam = team

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val pokemonName: TextView = view.findViewById(R.id.pokemon_name)
        val orderNumber: EditText = view.findViewById(R.id.orderNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.change_team_order_adapter, parent, false)
        return PokemonViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        Log.d("position", position.toString())
        holder.pokemonName.text = pokemonTeam.getPokemon(position).getName()
        holder.orderNumber.setText(position.toString())
    }

    override fun getItemCount(): Int = pokemonTeam.getSize()
}