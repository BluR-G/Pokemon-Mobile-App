package com.example.pokemon.menu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.objects.MoveData

class PokedexMoveAdapter(private val moveList: ArrayList<MoveData>) :
    RecyclerView.Adapter<PokedexMoveAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val move : TextView = view.findViewById(R.id.move)
        val level : TextView = view.findViewById(R.id.level)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pokedex_move_adapter, parent, false)
        return ViewHolder(layout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val move = moveList[position]
        holder.move.text = "Move: ${move.moveName}"
        holder.level.text = "Learned at: lvl ${move.level_learned_at}"
    }

    override fun getItemCount(): Int = moveList.size
}