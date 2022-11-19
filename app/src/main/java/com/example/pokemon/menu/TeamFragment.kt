package com.example.pokemon.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentTeamBinding


class TeamFragment : Fragment() {
    lateinit var menuActivity: MenuActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentTeamBinding.inflate(layoutInflater)
        binding.TeamGoToMainMenu.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_teamFragment_to_mainMenuFragment)
        }
        binding.addTeam.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_teamFragment_to_teamAddPokemonFragment)
        }
        menuActivity = context as MenuActivity

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonTeam = menuActivity.getTeam()
        val recyclerView = view.findViewById<RecyclerView>(R.id.pokemon_team_recycler_view)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = PokemonTeamMenuAdaptor(pokemonTeam)
        }
    }
}