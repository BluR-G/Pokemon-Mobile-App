package com.example.pokemon.menu

import android.annotation.SuppressLint
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
import android.widget.Toast

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
        holder.pokemonName.text = pokemon.getName()
        holder.pokemonName.setOnClickListener { view: View ->
            handleEvent(view,pokemon)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun handleEvent(view: View, pokemon: Pokemon) {
        val previousFragmentId = view.findNavController().currentBackStackEntry?.destination?.displayName
        if(previousFragmentId == context.getString(R.string.remove_pokemon_fragment)){
            removeFromTeam(view, pokemon)
        }

    }

    private fun removeFromTeam(view: View, pokemon: Pokemon) {
        val collection = menuActivity.getCollect()
        val team = menuActivity.getTeam()
        collection.switchPokemonToCollection(pokemon,team)
        Toast.makeText(context, "${pokemon.getName()} moved into collection",Toast.LENGTH_SHORT).show()
        view.findNavController().navigate(R.id.action_teamRemovePokemonFragment_to_teamFragment)
    }

    override fun getItemCount(): Int = pokemonTeam.getSize()
}