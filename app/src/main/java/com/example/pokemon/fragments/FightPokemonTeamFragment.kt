package com.example.pokemon.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.adapters.FightPokemonTeamAdapter
import com.example.pokemon.R
import com.example.pokemon.activities.FightActivity
import com.example.pokemon.databinding.FragmentFightPokemonTeamBinding
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam


class FightPokemonTeamFragment : Fragment()  {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<FightPokemonTeamAdapter.TeamViewHolder>? = null
    lateinit var fightActivity: FightActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFightPokemonTeamBinding.inflate(layoutInflater)
        binding.pokemonTeamFightGoToFightMenu.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_fightPokemonTeamFragment_to_fightMenuFragment)
        }
        fightActivity = context as FightActivity
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val team = fightActivity.getPokemonTeam()
        var recyclerView = itemView.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.apply {
            layoutManager = GridLayoutManager(activity,2)
            recyclerView.adapter = FightPokemonTeamAdapter(team, itemView.context)
        }
    }
}
