package com.example.pokemon.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.objects.PokemonCollection

class CollectionAdaptor(collection : PokemonCollection) : RecyclerView.Adapter<CollectionAdaptor.PokemonViewHolder>() {
    private var pokemonCollection = collection

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val pokemon: TextView = view.findViewById(R.id.pokemon_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pokemon_collection_adaptor, parent, false)
        return PokemonViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.pokemon.text = pokemonCollection.getPokemon(position).getName()
    }

    override fun getItemCount(): Int = pokemonCollection.getSize()
}