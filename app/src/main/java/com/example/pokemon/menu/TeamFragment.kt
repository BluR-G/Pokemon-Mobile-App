package com.example.pokemon.menu

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentTeamBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL


class TeamFragment : Fragment() {
    lateinit var menuActivity: MenuActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        menuActivity = context as MenuActivity
        val binding = FragmentTeamBinding.inflate(layoutInflater)
        val team = menuActivity.getTeam()

        binding.addTeam.isEnabled = team.getSize() < 6

        binding.addTeam.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_teamFragment_to_teamAddPokemonFragment)
        }

        binding.removeTeam.isEnabled = team.getSize() != 0

        binding.removeTeam.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_teamFragment_to_teamRemovePokemonFragment)
        }

        binding.viewCollection.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_teamFragment_to_viewCollectionFragment)
        }

        binding.changeOrder.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_teamFragment_to_changeTeamOrderFragment)
        }

        binding.TeamGoToMainMenu.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_teamFragment_to_mainMenuFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonTeam = menuActivity.getTeam()
        val recyclerView = view.findViewById<RecyclerView>(R.id.pokemon_team_recycler_view)
        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 3)
            recyclerView.adapter = PokemonTeamAdapter(pokemonTeam, view.context)
        }

    }
}