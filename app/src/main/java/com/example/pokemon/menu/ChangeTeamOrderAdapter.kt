package com.example.pokemon.menu

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.objects.PokemonTeam

class ChangeTeamOrderAdapter(team: PokemonTeam) : RecyclerView.Adapter<ChangeTeamOrderAdapter.PokemonViewHolder>() {
    private var pokemonTeam = team

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val pokemonImage: ImageView = view.findViewById(R.id.pokemon_sprite)
        val orderNumber: EditText = view.findViewById(R.id.orderNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.change_team_order_adapter, parent, false)
        return PokemonViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonTeam.getPokemon(position)
        val img = pokemon.getImages()
        val imgFront = img[0]
        val imageBytes = Base64.decode(imgFront, 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        //holder.pokemonImage.text = pokemonTeam.getPokemon(position).getName()
        holder.pokemonImage.setImageBitmap(image)
        holder.orderNumber.setText(position.toString())
    }

    override fun getItemCount(): Int = pokemonTeam.getSize()
}