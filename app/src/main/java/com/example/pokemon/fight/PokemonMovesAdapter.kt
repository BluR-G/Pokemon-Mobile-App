package com.example.pokemon.fight

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import kotlinx.coroutines.runBlocking

class PokemonMovesAdapter (private val pokemon: Pokemon, private val context: Context, private val battle: Battle): RecyclerView.Adapter<PokemonMovesAdapter.MovesViewHolder>() {
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
        val move = pokemon.getCurrentMoves()[position]
        holder.moveButton.text = move.moveName
        holder.moveButton.setOnClickListener{ view: View ->
            handleEvent(view, pokemon, move, position)
        }
    }

    private fun handleEvent(view: View, pokemon: Pokemon, move: MoveData, position: Int) {
        if(fightActivity.getFightState() == 0){
            battle.fight(view,move)
        } else if(fightActivity.getFightState() == -1){
            battle.replaceMove(pokemon,position)
            view.findNavController().navigate(R.id.action_fightFragment_to_fightMenuFragment)
        }
    }

    override fun getItemCount(): Int {
        return pokemon.getCurrentMoves().size
    }

}