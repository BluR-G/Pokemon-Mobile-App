package com.example.pokemon.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.pokemon.R
import com.example.pokemon.databinding.FragmentBagBinding

class BagFragment : Fragment() {
    lateinit var fightActivity: FightActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBagBinding.inflate(layoutInflater)
        fightActivity = context as FightActivity
        binding.bagGoToFightMenu.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_bagFragment_to_fightMenuFragment)
        }
        binding.pokeball.setOnClickListener { view: View ->
            fightActivity.getBattle().throwPokeball(view)
        }
        binding.potion.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_bagFragment_to_fightPokemonTeamFragment)
        }
        return binding.root
    }
}