package com.example.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.pokemon.databinding.FragmentBagBinding
import com.example.pokemon.databinding.FragmentFightPokemonTeamBinding

class BagFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBagBinding.inflate(layoutInflater)

        binding.bagGoToFightMenu.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_bagFragment_to_fightMenuFragment)
        }
        return binding.root
    }
}