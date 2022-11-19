package com.example.pokemon

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.databinding.FragmentFightMenuBinding
import com.example.pokemon.databinding.FragmentFightPokemonTeamBinding
import com.example.pokemon.objects.MoveData
import com.example.pokemon.objects.Pokemon
import com.example.pokemon.objects.PokemonTeam


class FightPokemonTeamFragment : Fragment()  {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<FightPokemonTeamAdapter.TeamViewHolder>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFightPokemonTeamBinding.inflate(layoutInflater)
        binding.pokemonTeamFightGoToFightMenu.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_fightPokemonTeamFragment_to_fightMenuFragment)
        }
        //Log.d("previous", findNavController().previousBackStackEntry?.destination?.id.toString())
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val team = initializeTeam()
        var recyclerView = itemView.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.apply {
            layoutManager = GridLayoutManager(activity,2)
            recyclerView.adapter = FightPokemonTeamAdapter(team, itemView.context)
        }
        //recyclerView.setLayoutFrozen(true)

    }
    fun initializeTeam() : PokemonTeam {
        val pokemonTeam = PokemonTeam()
        val types = arrayOf<String>("fire")
        val moves = ArrayList<MoveData>()
        val images = arrayOf<Drawable>()

        pokemonTeam.addPokemonToTeam(
            Pokemon("charizard","charizard",36,types,
                60,30,40,100,200,100, moves, images)
        )
        pokemonTeam.addPokemonToTeam(
            Pokemon("pikachu","pikachu",36,types,
                60,30,40,100,200,100, moves, images)
        )
        pokemonTeam.addPokemonToTeam(
            Pokemon("raichu","raichu",36,types,
                60,30,40,100,200,100, moves, images)
        )
        pokemonTeam.addPokemonToTeam(
                Pokemon("garyados","garyados",36,types,
                    60,30,40,100,200,100, moves, images)
        )
        pokemonTeam.addPokemonToTeam(
            Pokemon("raichu","zekrom",36,types,
                60,30,40,100,200,100, moves, images)
        )
        pokemonTeam.addPokemonToTeam(
            Pokemon("garyados","reshiram",36,types,
                60,30,40,100,200,100, moves, images)
        )


        return pokemonTeam
    }
}
