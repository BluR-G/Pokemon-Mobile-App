package com.example.pokemon.menu

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam
import android.content.Context

class PokemonTeamAdapter(team: PokemonTeam, private val context: Context) : RecyclerView.Adapter<PokemonTeamAdapter.PokemonViewHolder>(){
    private var pokemonTeam = team
    lateinit var menuActivity: MenuActivity

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val pokemonName: TextView = view.findViewById(R.id.pokemon_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        menuActivity = context as MenuActivity
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pokemon_team_menu_adapter, parent, false)
        return PokemonViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonTeam.getPokemon(position)
        //TODO: create click event depending where the adapter is being used from (team menu / remove pokemon)
        holder.pokemonName.text = pokemon.getName()
        holder.pokemonName.setOnClickListener { view: View ->
            handleEvent(view,pokemon)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun handleEvent(view: View, pokemon: Pokemon) {
        val previousFragmentId = view.findNavController().previousBackStackEntry?.destination?.displayName
        if (previousFragmentId != null) {
            Log.d("prev frag", previousFragmentId)
        }
        if(previousFragmentId == context.getString(R.string.remove_pokemon_fragment)){
            removeFromTeam(view, pokemon)
        }

    }

    private fun removeFromTeam(view: View, pokemon: Pokemon) {
        val collection = menuActivity.getCollect()
        val team = menuActivity.getTeam()
        collection.switchPokemonToCollection(pokemon,team)
        view.findNavController().navigate(R.id.action_teamRemovePokemonFragment_to_teamFragment)

    }

    override fun getItemCount(): Int = pokemonTeam.getSize()
}