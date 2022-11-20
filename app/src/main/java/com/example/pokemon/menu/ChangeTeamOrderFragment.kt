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
import com.example.pokemon.databinding.FragmentChangeTeamOrderBinding

class ChangeTeamOrderFragment : Fragment() {
    lateinit var menuActivity: MenuActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChangeTeamOrderBinding.inflate(layoutInflater)
        binding.apply.setOnClickListener { view : View -> changeOrder(view) }
        menuActivity = context as MenuActivity
        return binding.root
    }

    private fun changeOrder(view: View) {
        view.findNavController().navigate(R.id.action_changeTeamOrderFragment_to_teamFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonTeam = menuActivity.getTeam()
        val recyclerView = view.findViewById<RecyclerView>(R.id.change_team_order_recycler_view)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = ChangeTeamOrderAdapter(pokemonTeam)
        }

    }


}