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
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

class PokemonTeamAdapter(private var pokemonTeam: PokemonTeam, private var pokemonSprites: ArrayList<Bitmap>, private val context: Context) : RecyclerView.Adapter<PokemonTeamAdapter.PokemonViewHolder>(){
    lateinit var menuActivity: MenuActivity

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val pokemonView : LinearLayout = view.findViewById(R.id.pokemon_view)
        val pokemonName: TextView = view.findViewById(R.id.pokemon_name)
        val pokemonLevel: TextView = view.findViewById(R.id.pokemon_level)
        val pokemonSprite : ImageView = view.findViewById(R.id.pokemon_sprite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        menuActivity = context as MenuActivity
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pokemon_team_menu_adapter, parent, false)
        return PokemonViewHolder(layout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        //TODO: add image into adapter
        val pokemon = pokemonTeam.getPokemon(position)
        holder.pokemonSprite.setImageBitmap(pokemonSprites[position])
        holder.pokemonName.text = pokemon.getName()
        holder.pokemonLevel.text = "Lvl: ${pokemon.getLevel()}"
        holder.pokemonView.setOnClickListener { view: View ->
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