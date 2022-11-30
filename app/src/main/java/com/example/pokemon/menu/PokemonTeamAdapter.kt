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
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf

class PokemonTeamAdapter(private var pokemonTeam: PokemonTeam, private val context: Context) : RecyclerView.Adapter<PokemonTeamAdapter.PokemonViewHolder>(){
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
        val pokemon = pokemonTeam.getPokemon(position)
        val img = pokemon.getImages()
        val imgFront = img[0]
        val imageBytes = Base64.decode(imgFront, 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        holder.pokemonSprite.setImageBitmap(image)
        holder.pokemonName.text = pokemon.getName()
        holder.pokemonLevel.text = "Lvl: ${pokemon.getLevel()}"
        holder.pokemonView.setOnClickListener { view: View ->
            handleEvent(view,pokemon)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun handleEvent(view: View, pokemon: Pokemon) {
        val previousFragmentId = view.findNavController().currentBackStackEntry?.destination?.displayName
        if (previousFragmentId != null) {
            Log.d("id", previousFragmentId)
        }
        if(previousFragmentId == context.getString(R.string.remove_pokemon_fragment)){
            removeFromTeam(view, pokemon)
        } else if (previousFragmentId == context.getString(R.string.team_fragment)){
            val bundle = bundleOf("pokemon" to pokemon)
            menuActivity.setPokemon(pokemon)
            view.findNavController().navigate(R.id.action_teamFragment_to_pokedexFragment, bundle)
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