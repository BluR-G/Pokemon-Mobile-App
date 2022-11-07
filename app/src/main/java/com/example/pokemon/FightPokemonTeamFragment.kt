package com.example.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokemon.databinding.FragmentFightMenuBinding
import com.example.pokemon.databinding.FragmentFightPokemonTeamBinding


class FightPokemonTeamFragment : Fragment()  {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFightPokemonTeamBinding.inflate(layoutInflater)
        return binding.root
    }
}
