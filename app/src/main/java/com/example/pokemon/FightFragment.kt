package com.example.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.pokemon.databinding.FragmentFightBinding
import com.example.pokemon.databinding.FragmentFightPokemonTeamBinding

class FightFragment : Fragment()  {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFightBinding.inflate(layoutInflater)
        binding.fightGoToFightMenu.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_fightFragment_to_fightMenuFragment)
        }
        return binding.root
    }
}