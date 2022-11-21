package com.example.pokemon.fight

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon

class PokemonMovesAdapter (private val pokemon: Pokemon, private val context: Context, private val game: Game): RecyclerView.Adapter<PokemonMovesAdapter.MovesViewHolder>() {
    lateinit var fightActivity: FightActivity
    class MovesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val moveButton: Button = view.findViewById<Button>(R.id.move_text_item)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovesViewHolder {
        fightActivity = context as FightActivity
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.move_item, parent, false)

        return MovesViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MovesViewHolder, position: Int) {
        val move = pokemon.getMoves()[position]
        holder.moveButton.text = move.moveName
        holder.moveButton.setOnClickListener{ view: View ->
            handleEvent(view, pokemon, move)
        }
    }

    private fun handleEvent(view: View, pokemon: Pokemon, move: MoveData) {
        game.attack(move)
    }

    override fun getItemCount(): Int {
        return pokemon.getMoves().size
    }

}