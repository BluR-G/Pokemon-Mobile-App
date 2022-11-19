package com.example.pokemon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.activities.FightActivity
import com.example.pokemon.adapters.FightPokemonTeamAdapter
import com.example.pokemon.adapters.PokemonMovesAdapter
import com.example.pokemon.databinding.FragmentFightBinding

class FightFragment : Fragment()  {
    lateinit var fightActivity: FightActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFightBinding.inflate(layoutInflater)
        binding.fightGoToFightMenu.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_fightFragment_to_fightMenuFragment)
        }
        fightActivity = context as FightActivity
        return binding.root
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        var pokemon = fightActivity.getCurrentPokemon()
        var recyclerView = itemView.findViewById<RecyclerView>(R.id.move_recycler_view)
        recyclerView.apply {
            layoutManager = GridLayoutManager(activity,2)
            recyclerView.adapter = PokemonMovesAdapter(pokemon, itemView.context)
        }
    }
}